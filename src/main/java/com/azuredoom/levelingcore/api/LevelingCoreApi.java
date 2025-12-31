package com.azuredoom.levelingcore.api;

import com.azuredoom.levelingcore.LevelingCore;
import com.azuredoom.levelingcore.level.LevelService;

import java.util.Optional;

public final class LevelingCoreApi {

    private LevelingCoreApi() {}

    /**
     * Retrieves the {@link LevelService} instance if it is available and returns it as an {@code Optional}. This allows
     * safely handling scenarios where the leveling service might not be initialized.
     *
     * @return an {@code Optional} containing the {@link LevelService} instance if available; otherwise, an empty
     *         {@code Optional}.
     */
    public static Optional<LevelService> getLevelServiceIfPresent() {
        return Optional.ofNullable(LevelingCore.getLevelService());
    }
}
