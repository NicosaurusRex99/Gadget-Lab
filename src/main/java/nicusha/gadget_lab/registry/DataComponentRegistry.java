package nicusha.gadget_lab.registry;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.*;
import nicusha.gadget_lab.Main;
import nicusha.gadget_lab.component.MobFromUUIDComponent;

public class DataComponentRegistry {
    public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Main.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<MobFromUUIDComponent>> SELECTED_MOB = COMPONENTS.register("selected_mob", () -> new DataComponentType.Builder<MobFromUUIDComponent>().persistent(MobFromUUIDComponent.ENTITY_ID_COMPONENT_CODEC).networkSynchronized(MobFromUUIDComponent.STREAM_ENTITY_ID).build());
}



