package com.santipdr.copyl;

import com.santipdr.copyl.client.CopyLClientSetup;
import com.santipdr.copyl.common.block.ModBlocks;
import com.santipdr.copyl.common.block.entity.ModBlockEntities;
import com.santipdr.copyl.common.entity.ModEntities;
import com.santipdr.copyl.common.entity.ModSounds;
import com.santipdr.copyl.common.item.ModItems;
import com.santipdr.copyl.common.item.armor.ModArmor;
import com.santipdr.copyl.common.item.material.ModMaterialItems;
import com.santipdr.copyl.common.item.tool.ModTools;
import com.santipdr.copyl.common.world.ModWorldGenFeatures;
import com.santipdr.copyl.common.registry.ModCreativeTabs;
import com.santipdr.copyl.common.registry.ModRegistries;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(CopyL.MOD_ID)
public final class CopyL {
    public static final String MOD_ID = "copyl";

    public CopyL() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Las llamadas bootstrap fuerzan la inicialización de todos los RegistryObject antes
        // de que los DeferredRegister se conecten al bus.
        ModBlocks.bootstrap();
        ModBlockEntities.bootstrap();
        ModItems.bootstrap();
        ModMaterialItems.bootstrap();
        ModTools.bootstrap();
        ModArmor.bootstrap();
        ModEntities.bootstrap();
        ModWorldGenFeatures.bootstrap();
        ModSounds.bootstrap();
        ModCreativeTabs.bootstrap();

        ModRegistries.BLOCKS.register(modEventBus);
        ModRegistries.ITEMS.register(modEventBus);
        ModRegistries.BLOCK_ENTITIES.register(modEventBus);
        ModRegistries.ENTITY_TYPES.register(modEventBus);
        ModRegistries.FEATURES.register(modEventBus);
        ModRegistries.SOUND_EVENTS.register(modEventBus);
        ModRegistries.MENU_TYPES.register(modEventBus);
        ModRegistries.CREATIVE_TABS.register(modEventBus);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            CopyLClientSetup.registerConfigScreen();
        }
    }
}
