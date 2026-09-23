package com.santipdr.copyl.common.item.tool;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

/**
 * Tiers reconstruidos desde OrespawnToolMaterial.class.
 * Amethyst y Emerald comparten stats en el original.
 */
public final class CopyLToolTiers {
    public static final Tier AMETHYST = new ForgeTier(
            2, 1000, 6.5F, 3.0F, 12,
            BlockTags.NEEDS_IRON_TOOL,
            () -> Ingredient.EMPTY
    );

    public static final Tier EMERALD = new ForgeTier(
            2, 1000, 6.5F, 3.0F, 12,
            BlockTags.NEEDS_IRON_TOOL,
            () -> Ingredient.EMPTY
    );

    public static final Tier ULTIMATE = new ForgeTier(
            10, 3000, 15.0F, 36.0F, 100,
            BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.EMPTY
    );

    private CopyLToolTiers() {
    }
}
