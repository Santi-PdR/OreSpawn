package com.santipdr.copyl.common.entity;

import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.registry.ModRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;

/** Sonidos originales necesarios para el primer grupo de criaturas porteadas. */
public final class ModSounds {
    public static final RegistryObject<SoundEvent> ALIEN_LIVING = sound("entity.alien.living");
    public static final RegistryObject<SoundEvent> ALIEN_HURT = sound("entity.alien.hurt");

    public static final RegistryObject<SoundEvent> GAMMA_METROID_LIVING = sound("entity.gammametroid.living");
    public static final RegistryObject<SoundEvent> DUCK_HURT = sound("entity.duck.hurt");
    public static final RegistryObject<SoundEvent> ALOSAURUS_DEATH = sound("entity.alosaurus.death");

    public static final RegistryObject<SoundEvent> CRYO_LIVING = sound("entity.cryo.living");
    public static final RegistryObject<SoundEvent> CRYO_HURT = sound("entity.cryo.hurt");
    public static final RegistryObject<SoundEvent> CRYO_DEATH = sound("entity.cryo.death");

    private static RegistryObject<SoundEvent> sound(String id) {
        return ModRegistries.SOUND_EVENTS.register(id,
                () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CopyL.MOD_ID, id)));
    }

    public static void bootstrap() {
    }

    private ModSounds() {
    }
}
