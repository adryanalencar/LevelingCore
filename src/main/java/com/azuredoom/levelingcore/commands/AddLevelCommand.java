package com.azuredoom.levelingcore.commands;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.util.EventTitleUtil;

import javax.annotation.Nonnull;

import com.azuredoom.levelingcore.api.LevelingCoreApi;

/**
 * The AddLevelCommand class is responsible for handling the command logic to add levels to a player's progress using
 * the LevelingCore API. This command ensures that the leveling system is properly initialized before performing any
 * operations and updates the player's level accordingly. Feedback messages are sent to both the player and the command
 * executor.
 */
public class AddLevelCommand extends CommandBase {

    @Nonnull
    private final RequiredArg<PlayerRef> playerArg;

    @Nonnull
    private final RequiredArg<Integer> levelArg;

    public AddLevelCommand() {
        super("addlevel", "Add level to player");
        this.playerArg = this.withRequiredArg(
            "player",
            "server.commands.levelingcore.addlevel.desc",
            ArgTypes.PLAYER_REF
        );
        this.levelArg = this.withRequiredArg("level", "server.commands.levelingcore.addlevel.desc", ArgTypes.INTEGER);
    }

    @Override
    protected void executeSync(@Nonnull CommandContext commandContext) {
        if (LevelingCoreApi.getLevelServiceIfPresent().isEmpty()) {
            commandContext.sendMessage(Message.raw("Leveling Core is not initialized"));
            return;
        }
        var playerRef = this.playerArg.get(commandContext);
        var levelRef = this.levelArg.get(commandContext);
        var playerUUID = playerRef.getUuid();
        LevelingCoreApi.getLevelServiceIfPresent().get().addLevel(playerUUID, levelRef);
        var addedLevelMsg = "Added " + levelRef + " levels to " + playerRef.getUsername();
        var levelTotalMsg = "Player " + playerRef.getUsername() + " is now level " + LevelingCoreApi
            .getLevelServiceIfPresent()
            .get()
            .getLevel(playerUUID);
        EventTitleUtil.showEventTitleToPlayer(playerRef, Message.raw(levelTotalMsg), Message.raw(levelTotalMsg), true);
        commandContext.sendMessage(Message.raw(addedLevelMsg));
        commandContext.sendMessage(Message.raw(levelTotalMsg));
    }
}
