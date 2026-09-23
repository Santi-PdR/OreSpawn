package com.santipdr.copyl.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

/** Port directo de danger.orespawn.entity.RedCow del JAR 1.12.2 proporcionado. */
public final class RedCowEntity extends Cow {
    public RedCowEntity(EntityType<? extends RedCowEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Cow.createAttributes();
    }

    @Override
    protected void customServerAiStep() {
        if (random.nextInt(200) == 1) {
            setTarget(null);
        }
        super.customServerAiStep();
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        int apples = random.nextInt(3) + random.nextInt(1 + Math.max(0, looting));
        for (int i = 0; i < apples; i++) {
            spawnAtLocation(new ItemStack(Items.APPLE));
        }
        super.dropCustomDeathLoot(source, looting, recentlyHit);
    }

    public static boolean checkSpawnRules(EntityType<RedCowEntity> type, ServerLevelAccessor level,
                                          MobSpawnType reason, BlockPos pos, RandomSource random) {
        return Animal.checkAnimalSpawnRules(type, level, reason, pos, random);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Nullable
    @Override
    public RedCowEntity getBreedOffspring(ServerLevel level, AgeableMob mate) {
        return ModEntities.RED_COW.get().create(level);
    }
}
