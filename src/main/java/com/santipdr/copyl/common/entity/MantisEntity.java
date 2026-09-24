package com.santipdr.copyl.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

/** Port directo de danger.orespawn.entity.Mantis. El original no añade IA ni atributos propios a EntityMob. */
public final class MantisEntity extends Monster {
    public MantisEntity(EntityType<? extends MantisEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes();
    }

    /** El método original siempre devuelve 0; el modelo lo usa para la pose de los brazos. */
    public int getAttacking() {
        return 0;
    }

    public static boolean checkSpawnRules(EntityType<MantisEntity> type, ServerLevelAccessor level,
                                          MobSpawnType reason, BlockPos pos, RandomSource random) {
        return Monster.checkMonsterSpawnRules(type, level, reason, pos, random);
    }
}
