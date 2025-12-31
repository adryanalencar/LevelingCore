package com.azuredoom.levelingcore.level;

import com.azuredoom.levelingcore.events.*;

import java.util.UUID;

/**
 * Service interface for managing player levels and experience points (XP) within a leveling system. This interface
 * provides methods for retrieving and modifying XP, as well as managing listeners for level-related events.
 */
public interface LevelService {

    /**
     * Adds a specified number of levels to the current level of a player identified by their unique ID.
     *
     * @param playerId the unique identifier of the player
     * @param level    the number of levels to add
     */
    void addLevel(UUID playerId, int level);

    /**
     * Removes a specified number of levels from the current level of a player identified by their unique ID. If the
     * resulting level is less than zero, it will be clamped to zero.
     *
     * @param playerId the unique identifier of the player
     * @param level    the number of levels to remove
     */
    void removeLevel(UUID playerId, int level);

    /**
     * Sets the level of the player identified by their unique ID. If the specified level is less than zero, it will be
     * clamped to zero.
     *
     * @param playerId the unique identifier of the player
     * @param level    the level to set the player to
     * @return the updated level of the player after the operation
     */
    int setLevel(UUID playerId, int level);

    /**
     * Retrieves the current level of a player identified by their unique ID.
     *
     * @param playerId the unique identifier of the player
     * @return the current level of the player
     */
    int getLevel(UUID playerId);

    /**
     * Calculates the required experience points (XP) for reaching a specific level.
     *
     * @param level the target level for which the required XP is to be calculated. Must be a non-negative integer.
     * @return the amount of XP required to reach the specified level.
     */
    long getXpForLevel(int level);

    /**
     * Retrieves the current experience points (XP) of a player identified by their unique ID.
     *
     * @param playerId the unique identifier of the player
     * @return the current amount of XP the player has
     */
    long getXp(UUID playerId);

    /**
     * Adds experience points (XP) to a player identified by their unique ID. If the added XP exceeds the threshold for
     * the next level, the player's level may increase.
     *
     * @param playerId the unique identifier of the player to whom experience points should be added
     * @param amount   the number of experience points to add; must be a non-negative value
     */
    void addXp(UUID playerId, long amount);

    /**
     * Removes a specified number of experience points (XP) from a player identified by their unique ID. If the
     * resulting XP is less than zero, it will be clamped to zero.
     *
     * @param playerId the unique identifier of the player
     * @param amount   the number of experience points to remove; must be a non-negative value
     */
    void removeXp(UUID playerId, long amount);

    /**
     * Sets the experience points (XP) of a player identified by their unique ID. This method directly assigns the
     * provided XP value to the player, replacing their current XP. If the specified XP is less than zero, it may be
     * clamped to zero depending on the implementation.
     *
     * @param playerId the unique identifier of the player whose XP is to be set
     * @param xp       the number of experience points to assign to the player
     */
    void setXp(UUID playerId, long xp);

    void registerXpGainListener(XpGainListener listener);

    void registerXpLossListener(XpLossListener listener);

    void registerLevelDownListener(LevelDownListener listener);

    void registerLevelUpListener(LevelUpListener listener);
}
