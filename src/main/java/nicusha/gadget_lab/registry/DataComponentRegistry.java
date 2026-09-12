package nicusha.gadget_lab.registry;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.*;
import nicusha.gadget_lab.GadgetLab;
import nicusha.gadget_lab.component.CapturedMobsComponent;
import nicusha.gadget_lab.component.MobFromUUIDComponent;

import java.util.function.Supplier;

public class DataComponentRegistry {
    public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, GadgetLab.MODID);

    public static final Supplier<DataComponentType<CapturedMobsComponent>> CAPTURED_MOBS = COMPONENTS.register("captured_mobs", () -> DataComponentType.<CapturedMobsComponent>builder().persistent(CapturedMobsComponent.CODEC).networkSynchronized(CapturedMobsComponent.STREAM_CODEC).build());

}