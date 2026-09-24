package com.santipdr.copyl.common.block.entity;

import com.santipdr.copyl.common.block.ModBlocks;
import com.santipdr.copyl.common.registry.ModRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlockEntities {
    public static final RegistryObject<BlockEntityType<CornPlantBlockEntity>> CORN_PLANT =
            ModRegistries.BLOCK_ENTITIES.register("corn_plant", () ->
                    BlockEntityType.Builder.of(CornPlantBlockEntity::new, ModBlocks.CORN_PLANT.get()).build(null));

    public static void bootstrap() {}
    private ModBlockEntities() {}
}
