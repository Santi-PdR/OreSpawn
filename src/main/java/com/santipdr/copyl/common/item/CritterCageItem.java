package com.santipdr.copyl.common.item;

import com.santipdr.copyl.common.entity.CageProjectileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/** Port de danger.orespawn.items.CritterCage. */
public final class CritterCageItem extends Item {
    private static final List<CritterCageItem> FILLED_CAGES = new ArrayList<>();

    @Nullable
    private final Supplier<? extends EntityType<?>> entityType;
    private final float chance;

    public static CritterCageItem empty() {
        return new CritterCageItem(null, 1.0F, false);
    }

    public CritterCageItem(Supplier<? extends EntityType<?>> entityType, float chance) {
        this(entityType, chance, true);
    }

    private CritterCageItem(@Nullable Supplier<? extends EntityType<?>> entityType,
                            float chance,
                            boolean registerFilled) {
        super(new Item.Properties().stacksTo(16));
        this.entityType = entityType;
        this.chance = chance;
        if (registerFilled) {
            FILLED_CAGES.add(this);
        }
    }

    public float getChance() {
        return chance;
    }

    @Nullable
    public EntityType<?> getContainedType() {
        return entityType == null ? null : entityType.get();
    }

    @Nullable
    public static CritterCageItem getCageFromEntity(Entity entity) {
        for (CritterCageItem cage : FILLED_CAGES) {
            if (cage.getContainedType() == entity.getType()) {
                return cage;
            }
        }
        return null;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        throwCage(level, player, stack);
        if (!level.isClientSide && !player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();
        Level level = context.getLevel();

        if (player == null) {
            return InteractionResult.FAIL;
        }

        // La jaula vacía siempre se lanza, incluso si se hizo click sobre un bloque.
        if (entityType == null) {
            throwCage(level, player, stack);
            if (!level.isClientSide && !player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
            EntityType<?> type = getContainedType();
            Entity entity = type == null ? null : type.create(level);
            if (entity == null) {
                return InteractionResult.FAIL;
            }

            BlockPos pos = context.getClickedPos();
            entity.moveTo(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D,
                    entity.getYRot(), entity.getXRot());
            if (stack.hasCustomHoverName()) {
                entity.setCustomName(Component.literal(stack.getHoverName().getString()));
            }
            level.addFreshEntity(entity);
            CageProjectileEntity.emitReleaseEffects(serverLevel, pos);
            CageProjectileEntity.dropEmptyCage(serverLevel, pos);

            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    private void throwCage(Level level, Player player, ItemStack stack) {
        if (!level.isClientSide) {
            String customName = stack.hasCustomHoverName() ? stack.getHoverName().getString() : null;
            CageProjectileEntity projectile = new CageProjectileEntity(level, player, getContainedType(), customName);
            projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(projectile);
        }
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.EGG_THROW, SoundSource.PLAYERS, 1.0F, 1.5F);
    }
}
