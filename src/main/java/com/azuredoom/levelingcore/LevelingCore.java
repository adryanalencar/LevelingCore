package com.azuredoom.levelingcore;

import com.azuredoom.levelingcore.events.GainXPEventSystem;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.Config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.*;
import javax.annotation.Nonnull;

import com.azuredoom.levelingcore.commands.*;
import com.azuredoom.levelingcore.config.GUIConfig;
import com.azuredoom.levelingcore.config.internal.ConfigBootstrap;
import com.azuredoom.levelingcore.exceptions.LevelingCoreException;
import com.azuredoom.levelingcore.level.LevelServiceImpl;

public class LevelingCore extends JavaPlugin {

    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    public static final Path configPath = Paths.get("./mods/levelingcore_LevelingCore/data/config/");

    private static final ConfigBootstrap.Bootstrap bootstrap = ConfigBootstrap.bootstrap(configPath);

    private static LevelServiceImpl levelingService;

    private static LevelingCore INSTANCE;

    private final Config<GUIConfig> config;

    /**
     * Constructs a new {@code LevelingCore} instance and initializes the core components of the leveling system. This
     * constructor takes a non-null {@link JavaPluginInit} object to set up the necessary dependencies and
     * configurations required for the leveling system to function.
     *
     * @param init a {@link JavaPluginInit} instance used to initialize the plugin environment and dependencies. Must
     *             not be {@code null}.
     */
    public LevelingCore(@Nonnull JavaPluginInit init) {
        super(init);
        INSTANCE = this;
        config = this.withConfig("levelingcore", GUIConfig.CODEC);
    }

    @Override
    protected void setup() {
        super.setup();
        this.config.save();
        LOGGER.at(Level.INFO).log("Leveling Core initializing");
        levelingService = bootstrap.service();
        getCommandRegistry().registerCommand(new AddLevelCommand());
        getCommandRegistry().registerCommand(new CheckLevelCommand());
        getCommandRegistry().registerCommand(new AddXpCommand());
        getCommandRegistry().registerCommand(new SetLevelCommand());
        getCommandRegistry().registerCommand(new RemoveLevelCommand());
        getCommandRegistry().registerCommand(new RemoveXpCommand());
        getEntityStoreRegistry().registerSystem(new GainXPEventSystem());
    }

    @Override
    protected void shutdown() {
        super.shutdown();
        LOGGER.at(Level.INFO).log("Leveling Core shutting down");
        try {
            LevelingCore.bootstrap.closeable().close();
        } catch (Exception e) {
            throw new LevelingCoreException("Failed to close resources", e);
        }
    }

    // static void main() {
    // TODO: Remove once hooks into the player/mob kill events are found and integrable.
    // var testId = UUID.fromString("d3804858-4bb8-4026-ae21-386255ed467d");
    // if (LevelingCoreApi.getLevelServiceIfPresent().isPresent()) {
    // var levelingService = LevelingCoreApi.getLevelServiceIfPresent().get();
    // levelingService.addXp(testId, 500);
    // TODO: Move to chat or display based logging instead of loggers for gaining or lossing Levels/XP.
    // LOGGER.at(Level.INFO).log("Added 500 XP to player");
    // LOGGER.at(Level.INFO).log("Player level: " + levelingService.getLevel(testId));
    // }
    // }

    /**
     * Retrieves the {@link LevelServiceImpl} instance managed by the {@code LevelingCore} class. The
     * {@code LevelService} provides methods for managing player levels and experience points (XP).
     *
     * @return the {@link LevelServiceImpl} instance used by the leveling system.
     */
    public static LevelServiceImpl getLevelService() {
        return levelingService;
    }

    /**
     * Provides access to the singleton instance of the {@code LevelingCore} class. This instance serves as the primary
     * entry point for managing the core functionality of the leveling system, including initialization, configuration,
     * and lifecycle management.
     *
     * @return the singleton instance of {@code LevelingCore}.
     */
    public static LevelingCore getInstance() {
        return INSTANCE;
    }
}
