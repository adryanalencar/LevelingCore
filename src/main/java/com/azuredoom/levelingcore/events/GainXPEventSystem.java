package com.azuredoom.levelingcore.events;

import com.azuredoom.levelingcore.api.LevelingCoreApi;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.AllLegacyLivingEntityTypesQuery;
import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathSystems;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.flock.Flock;
import com.hypixel.hytale.server.flock.FlockPlugin;
import com.hypixel.hytale.server.npc.entities.NPCEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GainXPEventSystem extends DeathSystems.OnDeathSystem  {

    @Nullable
    @Override
    public Query<EntityStore> getQuery() {
        return NPCEntity.getComponentType();
    }

    @Override
    public void onComponentAdded(@Nonnull Ref ref, @Nonnull DeathComponent component, @Nonnull Store store, @Nonnull CommandBuffer commandBuffer) {
        NPCEntity entity = (NPCEntity) commandBuffer.getComponent(ref, NPCEntity.getComponentType());

        var test = entity.getDamageData().getAnyAttacker().getStore().getComponent(ref, Player.getComponentType());
        LevelingCoreApi.getLevelServiceIfPresent().ifPresent(levelService -> {
           levelService.addXp(test.getUuid(), (long) entity.getDamageData().getMaxDamageSuffered());
        });
    }
}