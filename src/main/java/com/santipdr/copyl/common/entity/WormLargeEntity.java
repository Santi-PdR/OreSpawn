package com.santipdr.copyl.common.entity;

import com.santipdr.copyl.common.entity.ai.LongRangeWanderGoal;
import com.santipdr.copyl.common.item.material.ModMaterialItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemLike;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

/** Port del WormLarge original de OreSpawn 1.12.2. */
public final class WormLargeEntity extends Monster {
    private int wormsSpawned;

    public WormLargeEntity(EntityType<? extends WormLargeEntity> type, Level level) {
        super(type, level);
        this.xpReward = 2050;
        this.noPhysics = true;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 90.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.20000000298023224D)
                .add(Attributes.ATTACK_DAMAGE, 18.0D)
                .add(Attributes.ARMOR, 14.0D);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new MoveThroughVillageGoal(this, 1.0D, false, 4, () -> false));
        goalSelector.addGoal(2, new LongRangeWanderGoal(this, 16, 1.0D));
        goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!isAlive()) return;

        WormMediumEntity medium = nearestMedium(8.0D, 8.0D, 8.0D);
        Player player = medium == null ? nearestPlayer(8.0D, 8.0D, 8.0D, false) : null;

        if (medium == null && player != null) {
            pointAt(player);
            BlockState state = blockAtYOffset(0.0D);
            if (state.is(Blocks.TALL_GRASS)) state = Blocks.AIR.defaultBlockState();
            if (!state.isAir()) {
                setDeltaMovement(getDeltaMovement().x, getDeltaMovement().y + 0.25D, getDeltaMovement().z);
                setPos(getX(), getY() + 0.10000000149011612D, getZ());
            } else {
                noPhysics = false;
            }
        } else {
            noPhysics = true;
            BlockState state = blockAtYOffset(3.5D);
            if (state.is(Blocks.TALL_GRASS)) state = Blocks.AIR.defaultBlockState();
            if (!state.isAir()) {
                setDeltaMovement(getDeltaMovement().x, getDeltaMovement().y + 0.10000000149011612D, getDeltaMovement().z);
                setPos(getX(), getY() + 0.05000000074505806D, getZ());
                if (!state.is(Blocks.GRASS_BLOCK) && !state.is(Blocks.DIRT) && !state.is(Blocks.STONE)) {
                    discard();
                    return;
                }
            }
        }

        if (noPhysics) {
            setDeltaMovement(0.0D, getDeltaMovement().y - 0.01D, 0.0D);
            zza = 0.0F;
        }

        if (!level().isClientSide && wormsSpawned == 0) {
            wormsSpawned = 1;
            for (int i = 0; i < 20; i++) {
                spawnMinion(ModEntities.SMALL_WORM.get(), 6);
                spawnMinion(ModEntities.MEDIUM_WORM.get(), 5);
            }
        }
    }

    private void spawnMinion(EntityType<? extends Mob> type, int spread) {
        if (!(level() instanceof ServerLevel server)) return;
        Mob mob = type.create(server);
        if (mob == null) return;
        double x = getX() + random.nextInt(spread) - random.nextInt(spread);
        double z = getZ() + random.nextInt(spread) - random.nextInt(spread);
        mob.moveTo(x, getY(), z, random.nextFloat() * 360.0F, 0.0F);
        mob.finalizeSpawn(server, server.getCurrentDifficultyAt(mob.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
        server.addFreshEntity(mob);
    }

    @Override
    protected void customServerAiStep() {
        if (isDeadOrDying()) return;
        if (!noPhysics) super.customServerAiStep();
        if (nearestMedium(8.0D, 8.0D, 8.0D) != null) return;

        Player target = nearestPlayer(8.0D, 6.0D, 8.0D, true);
        if (target == null) return;

        pointAt(target);
        getNavigation().moveTo(target, 1.0D);
        if (random.nextInt(10) != 1 || distanceTo(target) >= 3.0F) return;

        doHurtTarget(target);

        if (random.nextInt(4) == 1) {
            ItemStack armor = target.getItemBySlot(EquipmentSlot.FEET);
            EquipmentSlot slot = EquipmentSlot.FEET;
            if (!canStrip(armor)) {
                armor = target.getItemBySlot(EquipmentSlot.LEGS);
                slot = EquipmentSlot.LEGS;
            }
            if (canStrip(armor)) {
                stripAndThrow(target, slot, armor);
            }
        }

        if (random.nextInt(4) == 1) {
            ItemStack held = target.getItemBySlot(EquipmentSlot.MAINHAND);
            if (canStrip(held)) stripAndThrow(target, EquipmentSlot.MAINHAND, held);
        }
    }

    private static boolean canStrip(ItemStack stack) {
        return !stack.isEmpty();
    }

    private void stripAndThrow(Player player, EquipmentSlot slot, ItemStack original) {
        ItemStack stolen = original.copy();
        player.setItemSlot(slot, ItemStack.EMPTY);
        int remaining = stolen.getMaxDamage() - stolen.getDamageValue();
        int damage = remaining > 10 ? remaining / 10 : 1;
        if (stolen.isDamageableItem()) {
            stolen.setDamageValue(Math.min(stolen.getMaxDamage(), stolen.getDamageValue() + damage));
            if (stolen.getDamageValue() >= stolen.getMaxDamage()) stolen.shrink(1);
        }
        if (!stolen.isEmpty()) {
            double x = getX() + random.nextInt(5) - random.nextInt(5);
            double z = getZ() + random.nextInt(5) - random.nextInt(5);
            level().addFreshEntity(new ItemEntity(level(), x, getY() + 3.0D, z, stolen));
        }
    }

    private WormMediumEntity nearestMedium(double x, double y, double z) {
        return level().getEntitiesOfClass(WormMediumEntity.class, getBoundingBox().inflate(x, y, z), Entity::isAlive)
                .stream().min(Comparator.comparingDouble(this::distanceToSqr)).orElse(null);
    }

    private Player nearestPlayer(double x, double y, double z, boolean rejectCreative) {
        return level().getEntitiesOfClass(Player.class, getBoundingBox().inflate(x, y, z),
                        p -> p.isAlive() && (!rejectCreative || !p.getAbilities().instabuild))
                .stream().min(Comparator.comparingDouble(this::distanceToSqr)).orElse(null);
    }

    private void pointAt(LivingEntity entity) {
        double dx = entity.getX() - getX();
        double dz = entity.getZ() - getZ();
        float yaw = (float) (Math.atan2(dz, dx) * 180.0D / Math.PI) - 90.0F;
        setYRot(yaw);
        yHeadRot = yaw;
    }

    private BlockState blockAtYOffset(double offset) {
        return level().getBlockState(BlockPos.containing(getX(), getY() + offset, getZ()));
    }

    @Override
    public void tick() {
        if (isPersistenceRequired()) noPhysics = false;
        super.tick();
        setDeltaMovement(getDeltaMovement().x, getDeltaMovement().y * 0.85D, getDeltaMovement().z);
    }

    @Override public boolean isPushable() { return true; }
    @Override public void push(Entity entity) {}
    @Override protected void pushEntities() {}
    @Override public boolean isIgnoringBlockTriggers() { return true; }

    @Override
    public boolean causeFallDamage(float distance, float multiplier, DamageSource source) {
        return !noPhysics && super.causeFallDamage(distance, multiplier, source);
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
        if (!noPhysics) super.checkFallDamage(y, onGround, state, pos);
        else fallDistance = 0.0F;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return source.is(DamageTypes.IN_WALL) ? false : super.hurt(source, amount);
    }

    public static boolean checkSpawnRules(EntityType<WormLargeEntity> type, ServerLevelAccessor level,
                                          MobSpawnType reason, BlockPos pos, RandomSource random) {
        if (reason == MobSpawnType.SPAWNER) return true;
        if (pos.getY() < 50) return false;
        if (!level.getEntitiesOfClass(WormLargeEntity.class, new AABB(pos).inflate(32.0D, 8.0D, 32.0D)).isEmpty()) return false;

        for (int x = -6; x <= 6; x++) {
            for (int z = -6; z <= 6; z++) {
                for (int y = -2; y >= -8; y--) {
                    if (level.getBlockState(pos.offset(x, y, z)).isAir()) return false;
                }
            }
        }
        for (int x = -6; x <= 6; x++) {
            for (int z = -6; z <= 6; z++) {
                for (int y = 2; y <= 8; y++) {
                    if (!level.getBlockState(pos.offset(x, y, z)).isAir()) return false;
                }
            }
        }
        return true;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason,
                                        @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        if (reason == MobSpawnType.SPAWNER) wormsSpawned = 1;
        return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
    }

    @Override public boolean removeWhenFarAway(double distanceToClosestPlayer) { return false; }
    @Override protected SoundEvent getAmbientSound() { return null; }
    @Override protected SoundEvent getHurtSound(DamageSource source) { return ModSounds.BIG_SPLAT.get(); }
    @Override protected SoundEvent getDeathSound() { return ModSounds.ALOSAURUS_DEATH.get(); }
    @Override protected float getSoundVolume() { return 0.5F; }
    @Override public float getVoicePitch() { return 1.0F; }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        dropRand(Items.ITEM_FRAME, 1);
        for (int i = 0; i < 6; i++) dropRand(Items.ROTTEN_FLESH, 1);
        for (int i = 0; i < 6; i++) dropRand(Items.LEATHER, 1);
        for (int i = 0; i < 8; i++) dropRand(Blocks.DIRT, 1);
        for (int i = 0; i < 16; i++) dropRand(Items.GOLD_NUGGET, 1);
        for (int i = 0; i < 5; i++) dropRand(Items.DIAMOND, 1);
        for (int i = 0; i < 4; i++) dropRand(ModMaterialItems.URANIUM_NUGGET.get(), 1);
        for (int i = 0; i < 4; i++) dropRand(ModMaterialItems.TITANIUM_NUGGET.get(), 1);
    }

    private void dropRand(ItemLike item, int count) {
        double x = getX() + random.nextInt(4) - random.nextInt(4);
        double y = getY() + 2.5D + random.nextInt(4);
        double z = getZ() + random.nextInt(4) - random.nextInt(4);
        level().addFreshEntity(new ItemEntity(level(), x, y, z, new ItemStack(item, count)));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("wormsSpawned", wormsSpawned);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        wormsSpawned = tag.getInt("wormsSpawned");
    }
}
