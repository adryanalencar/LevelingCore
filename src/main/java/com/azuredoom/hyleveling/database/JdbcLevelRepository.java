package com.azuredoom.hyleveling.database;

import com.azuredoom.hyleveling.HyLevelingException;
import com.azuredoom.hyleveling.level.PlayerLevelData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.UUID;
import javax.sql.DataSource;

public class JdbcLevelRepository implements LevelRepository {

    private final DataSource dataSource;

    public JdbcLevelRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        var sql = """
            CREATE TABLE IF NOT EXISTS player_levels (
                player_id VARCHAR(36) PRIMARY KEY,
                xp BIGINT NOT NULL
            )
            """;

        try (
            Connection connection = dataSource.getConnection();
            Statement stmt = connection.createStatement()
        ) {
            stmt.execute(sql);
        } catch (Exception e) {
            throw new HyLevelingException("Failed to create player_levels table", e);
        }
    }

    @Override
    public void save(PlayerLevelData data) {
        var updateSql = "UPDATE player_levels SET xp = ? WHERE player_id = ?";
        var insertSql = "INSERT INTO player_levels (player_id, xp) VALUES (?, ?)";

        try (Connection connection = dataSource.getConnection()) {
            int updated;
            try (PreparedStatement ps = connection.prepareStatement(updateSql)) {
                ps.setLong(1, data.getXp());
                ps.setString(2, data.getPlayerId().toString());
                updated = ps.executeUpdate();
            }

            if (updated == 0) {
                try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
                    ps.setString(1, data.getPlayerId().toString());
                    ps.setLong(2, data.getXp());
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            throw new HyLevelingException("Failed to save player level data", e);
        }
    }

    @Override
    public PlayerLevelData load(UUID id) {
        var sql = "SELECT xp FROM player_levels WHERE player_id = ?";

        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setString(1, id.toString());

            var rs = ps.executeQuery();
            if (rs.next()) {
                var data = new PlayerLevelData(id);
                data.setXp(rs.getLong("xp"));
                return data;
            }
            return null;
        } catch (Exception e) {
            throw new HyLevelingException("Failed to load player level data", e);
        }
    }

    @Override
    public boolean exists(UUID id) {
        var sql = "SELECT 1 FROM player_levels WHERE player_id = ?";

        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setString(1, id.toString());
            var rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            throw new HyLevelingException("exists() failed", e);
        }
    }

    @Override
    public void close() {
        try {
            if (dataSource instanceof AutoCloseable c) {
                c.close();
            }
        } catch (Exception e) {
            throw new HyLevelingException("Failed to close JDBC datasource", e);
        }
    }
}
