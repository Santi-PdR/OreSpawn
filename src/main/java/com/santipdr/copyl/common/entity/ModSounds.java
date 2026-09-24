package com.santipdr.copyl.common.entity;

import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.registry.ModRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.stream.IntStream;

/** Sonidos originales necesarios para las criaturas porteadas. */
public final class ModSounds {
    public static final RegistryObject<SoundEvent> ALIEN_LIVING = sound("entity.alien.living");
    public static final RegistryObject<SoundEvent> ALIEN_HURT = sound("entity.alien.hurt");
    public static final RegistryObject<SoundEvent> GAMMA_METROID_LIVING = sound("entity.gammametroid.living");
    public static final RegistryObject<SoundEvent> DUCK_HURT = sound("entity.duck.hurt");
    public static final RegistryObject<SoundEvent> ALOSAURUS_LIVING = sound("entity.alosaurus.living");
    public static final RegistryObject<SoundEvent> ALOSAURUS_HURT = sound("entity.alosaurus.hurt");
    public static final RegistryObject<SoundEvent> ALOSAURUS_DEATH = sound("entity.alosaurus.death");
    public static final RegistryObject<SoundEvent> CRYO_LIVING = sound("entity.cryo.living");
    public static final RegistryObject<SoundEvent> CRYO_HURT = sound("entity.cryo.hurt");
    public static final RegistryObject<SoundEvent> CRYO_DEATH = sound("entity.cryo.death");
    public static final RegistryObject<SoundEvent> DRAGONFLY_LIVING = sound("entity.dragonfly.living");
    public static final RegistryObject<SoundEvent> DRAGONFLY_HURT = sound("entity.dragonfly.hurt");
    public static final RegistryObject<SoundEvent> DRAGONFLY_DEATH = sound("entity.dragonfly.death");
    public static final RegistryObject<SoundEvent> MOSQUITO_LIVING = sound("entity.mosquito.living");
    public static final RegistryObject<SoundEvent> LITTLE_SPLAT = sound("entity.misc.little_splat");
    public static final RegistryObject<SoundEvent> BIG_SPLAT = sound("entity.misc.big_splat");
    public static final List<RegistryObject<SoundEvent>> BIRD_SOUNDS = IntStream.rangeClosed(1, 23).mapToObj(i -> sound("entity.bird.bird" + i)).toList();
    public static final List<RegistryObject<SoundEvent>> STINKBUG_FARTS = IntStream.rangeClosed(1, 9).mapToObj(i -> sound("entity.stinkbug.fart" + i)).toList();

    public static SoundEvent randomBirdSound(RandomSource random) {
        return BIRD_SOUNDS.get(random.nextInt(BIRD_SOUNDS.size())).get();
    }

    public static SoundEvent randomStinkBugFart(RandomSource random) {
        return STINKBUG_FARTS.get(random.nextInt(STINKBUG_FARTS.size())).get();
    }

    private static RegistryObject<SoundEvent> sound(String id) {
        return ModRegistries.SOUND_EVENTS.register(id,
                () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CopyL.MOD_ID, id)));
    }

    public static void bootstrap() {}
    private ModSounds() {}
}
