package nicusha.gadget_lab.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.*;


import static nicusha.gadget_lab.Main.MODID;

public class CreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATIVE_TAB = TABS.register("tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup." + MODID)).icon(() -> new ItemStack(ItemRegistry.pocket_watch.get())).build());


}
