package com.azuredoom.levelingcore.commands;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import com.hypixel.hytale.server.core.universe.PlayerRef;

import javax.annotation.Nonnull;

import com.azuredoom.levelingcore.api.LevelingCoreApi;

public class RemoveLevelCommand extends CommandBase {

    @Nonnull
    private final RequiredArg<PlayerRef> playerArg;

    @Nonnull
    private final RequiredArg<Integer> levelArg;

    public RemoveLevelCommand() {
        super("removelevel", "Remove level from player");
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
        LevelingCoreApi.getLevelServiceIfPresent().get().removeLevel(playerUUID, levelRef);
        commandContext.sendMessage(Message.raw("Removed " + levelRef + " levels from " + playerRef.getUsername()));
        commandContext.sendMessage(Message.raw("Player " + playerRef.getUsername() + " now has " + LevelingCoreApi.getLevelServiceIfPresent().get().getLevel(playerUUID) + " levels"));
    }
}
