package com.santipdr.copyl.common.registry;

import com.santipdr.copyl.common.item.ModItems;
import com.santipdr.copyl.common.item.armor.ModArmor;
import com.santipdr.copyl.common.item.material.ModMaterialItems;
import com.santipdr.copyl.common.item.tool.ModTools;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

public final class ModCreativeTabs {
    public static final RegistryObject<CreativeModeTab> COPYL_TAB = ModRegistries.CREATIVE_TABS.register("copyl_tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.copyl")).icon(() -> new ItemStack(ModTools.ULTIMATE_SWORD.get())).displayItems((params, output) -> {
        output.accept(ModItems.TITANIUM_ORE.get()); output.accept(ModItems.URANIUM_ORE.get()); output.accept(ModItems.AMETHYST_ORE.get());
        output.accept(ModItems.TITANIUM_BLOCK.get()); output.accept(ModItems.URANIUM_BLOCK.get()); output.accept(ModItems.AMETHYST_BLOCK.get()); output.accept(ModItems.EXTREME_TORCH.get());
        output.accept(ModItems.ALIEN_ORE.get()); output.accept(ModItems.ALOSAURUS_ORE.get()); output.accept(ModItems.BARYONYX_ORE.get()); output.accept(ModItems.BEAVER_ORE.get()); output.accept(ModItems.BIRD_ORE.get()); output.accept(ModItems.BRUTALFLY_ORE.get()); output.accept(ModItems.CAMARASAURUS_ORE.get()); output.accept(ModItems.CASSOWARY_ORE.get()); output.accept(ModItems.CAVEFISHER_ORE.get()); output.accept(ModItems.COW_ORE.get()); output.accept(ModItems.CREEPER_ORE.get()); output.accept(ModItems.CRYOLOPHOSAURUS_ORE.get()); output.accept(ModItems.DOOMWORM_ORE.get()); output.accept(ModItems.DRAGONFLY_ORE.get()); output.accept(ModItems.GAMMAMETROID_ORE.get()); output.accept(ModItems.GHAST_ORE.get()); output.accept(ModItems.HORSE_ORE.get()); output.accept(ModItems.KYUUBI_ORE.get()); output.accept(ModItems.LARGEWORM_ORE.get()); output.accept(ModItems.MANTIS_ORE.get()); output.accept(ModItems.MEDIUMWORM_ORE.get()); output.accept(ModItems.MOTHRA_ORE.get()); output.accept(ModItems.NASTYSAURUS_ORE.get()); output.accept(ModItems.PIG_ORE.get()); output.accept(ModItems.POINTYSAURUS_ORE.get()); output.accept(ModItems.REDCOW_ORE.get()); output.accept(ModItems.SMALLWORM_ORE.get()); output.accept(ModItems.SPYRO_ORE.get()); output.accept(ModItems.STINKBUG_ORE.get()); output.accept(ModItems.TREX_ORE.get()); output.accept(ModItems.VELOCITYRAPTOR_ORE.get()); output.accept(ModItems.ZOMBIE_ORE.get());
        output.accept(ModItems.ALIEN_EGG.get()); output.accept(ModItems.ALOSAURUS_EGG.get()); output.accept(ModItems.BARYONYX_EGG.get()); output.accept(ModItems.BEAVER_EGG.get()); output.accept(ModItems.BIRD_EGG.get()); output.accept(ModItems.BUTTERFLY_EGG.get()); output.accept(ModItems.CASSOWARY_EGG.get()); output.accept(ModItems.CAMARASAURUS_EGG.get()); output.accept(ModItems.CAVEFISHER_EGG.get()); output.accept(ModItems.DRAGONFLY_EGG.get()); output.accept(ModItems.FIREFLY_EGG.get()); output.accept(ModItems.MOSQUITO_EGG.get()); output.accept(ModItems.MOTH_EGG.get()); output.accept(ModItems.MANTIS_EGG.get()); output.accept(ModItems.SMALLWORM_EGG.get()); output.accept(ModItems.POINTYSAURUS_EGG.get()); output.accept(ModItems.GAMMAMETROID_EGG.get()); output.accept(ModItems.CRYOLOPHOSAURUS_EGG.get()); output.accept(ModItems.REDCOW_EGG.get()); output.accept(ModItems.STINKBUG_EGG.get()); output.accept(ModItems.RED_ANT_EGG.get()); output.accept(ModItems.TERMITE_EGG.get()); output.accept(ModItems.EMPTY_CAGE.get()); output.accept(ModItems.ALIEN_CAGE.get()); output.accept(ModItems.ALOSAURUS_CAGE.get()); output.accept(ModItems.BARYONYX_CAGE.get()); output.accept(ModItems.BEAVER_CAGE.get()); output.accept(ModItems.CASSOWARY_CAGE.get()); output.accept(ModItems.CAMARASAURUS_CAGE.get()); output.accept(ModItems.CAVEFISHER_CAGE.get()); output.accept(ModItems.DRAGONFLY_CAGE.get()); output.accept(ModItems.FIREFLY_CAGE.get()); output.accept(ModItems.GAMMAMETROID_CAGE.get()); output.accept(ModItems.MANTIS_CAGE.get()); output.accept(ModItems.SMALLWORM_CAGE.get()); output.accept(ModItems.REDCOW_CAGE.get()); output.accept(ModItems.STINKBUG_CAGE.get()); output.accept(ModItems.MANTIS_CLAW.get());
        output.accept(ModMaterialItems.TITANIUM_NUGGET.get()); output.accept(ModMaterialItems.URANIUM_NUGGET.get()); output.accept(ModMaterialItems.TITANIUM_INGOT.get()); output.accept(ModMaterialItems.URANIUM_INGOT.get()); output.accept(ModMaterialItems.AMETHYST.get()); output.accept(ModMaterialItems.MOTH_SCALE.get());
        addToolSet(output, ModTools.AMETHYST_SWORD, ModTools.AMETHYST_PICKAXE, ModTools.AMETHYST_AXE, ModTools.AMETHYST_SHOVEL, ModTools.AMETHYST_HOE);
        addToolSet(output, ModTools.EMERALD_SWORD, ModTools.EMERALD_PICKAXE, ModTools.EMERALD_AXE, ModTools.EMERALD_SHOVEL, ModTools.EMERALD_HOE);
        addToolSet(output, ModTools.ULTIMATE_SWORD, ModTools.ULTIMATE_PICKAXE, ModTools.ULTIMATE_AXE, ModTools.ULTIMATE_SHOVEL, ModTools.ULTIMATE_HOE);
        addArmorSet(output, ModArmor.AMETHYST_HELMET, ModArmor.AMETHYST_CHESTPLATE, ModArmor.AMETHYST_LEGGINGS, ModArmor.AMETHYST_BOOTS);
        addArmorSet(output, ModArmor.EMERALD_HELMET, ModArmor.EMERALD_CHESTPLATE, ModArmor.EMERALD_LEGGINGS, ModArmor.EMERALD_BOOTS);
        addArmorSet(output, ModArmor.MOTH_HELMET, ModArmor.MOTH_CHESTPLATE, ModArmor.MOTH_LEGGINGS, ModArmor.MOTH_BOOTS);
        addArmorSet(output, ModArmor.ULTIMATE_HELMET, ModArmor.ULTIMATE_CHESTPLATE, ModArmor.ULTIMATE_LEGGINGS, ModArmor.ULTIMATE_BOOTS);
    }).build());
    @SafeVarargs private static void addToolSet(CreativeModeTab.Output output, RegistryObject<? extends net.minecraft.world.item.Item>... items) { for (RegistryObject<? extends net.minecraft.world.item.Item> item : items) output.accept(item.get()); }
    @SafeVarargs private static void addArmorSet(CreativeModeTab.Output output, RegistryObject<? extends net.minecraft.world.item.Item>... items) { for (RegistryObject<? extends net.minecraft.world.item.Item> item : items) output.accept(item.get()); }
    public static void bootstrap() {}
    private ModCreativeTabs() {}
}
