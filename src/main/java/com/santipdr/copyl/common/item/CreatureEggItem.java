package com.santipdr.copyl.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

/**
 * Port de danger.orespawn.items.ItemGenericEgg.
 * El huevo solo actua al usarse sobre un bloque: crea la entidad un bloque por encima y
 * consume un item salvo en creativo.
 */
public final class CreatureEggItem extends Item {
    private final Supplier<? extends EntityType<?>> entityType;

    public CreatureEggItem(Supplier<? extends EntityType<?>> entityType) {
        super(new Item.Properties());
        this.entityType = entityType;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        Entity entity = entityType.get().create(level);
        if (entity == null) {
            return InteractionResult.FAIL;
        }

        BlockPos pos = context.getClickedPos();
        entity.moveTo(pos.getX(), pos.getY() + 1.0D, pos.getZ(), entity.getYRot(), entity.getXRot());
        level.addFreshEntity(entity);

        if (context.getPlayer() == null || !context.getPlayer().getAbilities().instabuild) {
            context.getItemInHand().shrink(1);
        }
        return InteractionResult.SUCCESS;
    }
}
