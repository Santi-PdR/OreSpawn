package com.santipdr.copyl.common.entity;

import com.santipdr.copyl.common.entity.ai.LegacyWanderGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/** Port de danger.orespawn.entity.Baryonyx del JAR 1.12.2 proporcionado. */
public final class BaryonyxEntity extends Animal {
    private static final double MOVE_SPEED = 0.25D;
    private int closest = 99999;
    private int tx;
    private int ty;
    private int tz;

    public BaryonyxEntity(EntityType<? extends BaryonyxEntity> type, Level level) {
        super(type, level);
        this.xpReward = 5;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.MOVEMENT_SPEED, MOVE_SPEED)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
        goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Monster.class, 8.0F, 1.0D, 1.399999976158142D));
        goalSelector.addGoal(4, new PanicGoal(this, 1.5D));
        goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 12.0F));
        goalSelector.addGoal(6, new LegacyWanderGoal(this, 1.0D));
        goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    @Override
    public void tick() {
        var speed = getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed != null && speed.getBaseValue() != MOVE_SPEED) {
            speed.setBaseValue(MOVE_SPEED);
        }
        super.tick();
    }

    @Override
    protected void customServerAiStep() {
        if (random.nextInt(200) == 1) {
            setTarget(null);
        }

        // OreSpawn's PlayNicely default is the destructive behaviour: seek grass,
        // turn it into dirt, heal and burp. The compatibility toggle itself has
        // not been ported yet, so this preserves that original default.
        if (random.nextInt(60) == 0) {
            closest = 99999;
            tx = ty = tz = 0;

            for (int radius = 1; radius < 11; radius++) {
                int vertical = Math.min(radius, 2);
                if (scanIt((int) getX(), (int) getY() + 1, (int) getZ(), radius, vertical, radius)) {
                    break;
                }
                if (radius >= 6) {
                    radius++;
                }
            }

            if (closest < 99999) {
                getNavigation().moveTo(tx, ty, tz, 1.0D);
                if (closest < 12) {
                    if (level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                        level().setBlock(new BlockPos(tx, ty, tz), Blocks.DIRT.defaultBlockState(), 3);
                    }
                    heal(1.0F);
                    playSound(SoundEvents.PLAYER_BURP, 1.0F, random.nextFloat() * 0.2F + 0.9F);
                }
            }
        }

        super.customServerAiStep();
    }

    private boolean scanIt(int x, int y, int z, int dx, int dy, int dz) {
        int found = 0;

        for (int i = -dy; i <= dy; i++) {
            for (int j = -dz; j <= dz; j++) {
                found += inspectGrass(x + dx, y + i, z + j, dx * dx + j * j + i * i);
                found += inspectGrass(x - dx, y + i, z + j, dx * dx + j * j + i * i);
            }
        }

        for (int i = -dx; i <= dx; i++) {
            for (int j = -dz; j <= dz; j++) {
                found += inspectGrass(x + i, y + dy, z + j, dy * dy + j * j + i * i);
                found += inspectGrass(x + i, y - dy, z + j, dy * dy + j * j + i * i);
            }
        }

        for (int i = -dx; i <= dx; i++) {
            for (int j = -dy; j <= dy; j++) {
                found += inspectGrass(x + i, y + j, z + dz, dz * dz + j * j + i * i);
                found += inspectGrass(x + i, y + j, z - dz, dz * dz + j * j + i * i);
            }
        }

        return found != 0;
    }

    private int inspectGrass(int x, int y, int z, int distance) {
        BlockPos pos = new BlockPos(x, y, z);
        Block block = level().getBlockState(pos).getBlock();
        if (block == Blocks.GRASS_BLOCK && distance < closest) {
            closest = distance;
            tx = x;
            ty = y;
            tz = z;
            return 1;
        }
        return 0;
    }

    public static boolean checkSpawnRules(EntityType<BaryonyxEntity> type, ServerLevelAccessor level,
                                          MobSpawnType reason, BlockPos pos, RandomSource random) {
        return pos.getY() >= 50 && level.getLevel().isDay();
    }

    @Override
    public boolean checkSpawnRules(net.minecraft.world.level.LevelAccessor level, MobSpawnType reason) {
        if (getY() < 50.0D || !level().isDay()) {
            return false;
        }
        return findBuddies() <= 8;
    }

    private int findBuddies() {
        AABB area = getBoundingBox().inflate(20.0D, 10.0D, 20.0D);
        List<BaryonyxEntity> buddies = level().getEntitiesOfClass(BaryonyxEntity.class, area);
        return buddies.size();
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        if (isBaby() || isPersistenceRequired()) {
            return false;
        }
        return true;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.DUCK_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.DUCK_HURT.get();
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        int count = random.nextInt(5) + 2;
        for (int i = 0; i < count; i++) {
            spawnAtLocation(Items.BEEF);
        }
    }

    /** El helper original llamado isWheat realmente comprobaba una manzana normal. */
    public boolean isWheat(ItemStack stack) {
        return !stack.isEmpty() && stack.is(Items.APPLE);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.GOLDEN_APPLE);
    }

    @Nullable
    @Override
    public BaryonyxEntity getBreedOffspring(ServerLevel level, AgeableMob mate) {
        return (BaryonyxEntity) getType().create(level);
    }
}
