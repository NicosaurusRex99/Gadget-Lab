package nicusha.gadget_lab.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static nicusha.gadget_lab.GadgetLab.MODID;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> SPRAY = register("spray");


    private static <T extends SoundEvent> DeferredHolder<SoundEvent, SoundEvent> register(String registryName) {
        DeferredHolder<SoundEvent, SoundEvent> sounds = SOUNDS.register(registryName, () -> createSoundEvent(registryName));
        return sounds;
    }

    private static SoundEvent createSoundEvent(String soundPath) {
        return SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MODID, soundPath));
    }
}
