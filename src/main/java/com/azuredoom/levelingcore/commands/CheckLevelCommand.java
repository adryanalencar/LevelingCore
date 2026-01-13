package com.azuredoom.levelingcore.commands;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import com.hypixel.hytale.server.core.universe.PlayerRef;

import javax.annotation.Nonnull;

import com.azuredoom.levelingcore.api.LevelingCoreApi;

public class CheckLevelCommand extends CommandBase {

    @Nonnull
    private final RequiredArg<PlayerRef> playerArg;

    public CheckLevelCommand() {
        super("checklevel", "Check level of player");
        this.playerArg = this.withRequiredArg(
            "player",
            "server.commands.levelingcore.checklevel.desc",
            ArgTypes.PLAYER_REF
        );
    }

    @Override
    protected void executeSync(@Nonnull CommandContext commandContext) {
        if (LevelingCoreApi.getLevelServiceIfPresent().isEmpty()) {
            commandContext.sendMessage(Message.raw("Leveling Core is not initialized"));
            return;
        }
        var playerRef = this.playerArg.get(commandContext);
        var playerUUID = playerRef.getUuid();
        commandContext.sendMessage(
            Message.raw(
                playerRef.getUsername() + " level is: " + LevelingCoreApi.getLevelServiceIfPresent()
                    .get()
                    .getLevel(playerUUID)
            )
        );
    }
}
