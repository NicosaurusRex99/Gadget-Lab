package nicusha.gadget_lab.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.*;
import nicusha.gadget_lab.Main;


import static nicusha.gadget_lab.Main.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Main.MODID)
public class CreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<CreativeModeTab> CREATIVE_TAB = TABS.register("tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup." + MODID)).icon(() -> new ItemStack(ItemRegistry.pocket_watch.get())).build());



    @SubscribeEvent
    public static void creativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == CREATIVE_TAB.get()) {
            for (var regObj : ItemRegistry.ITEMS.getEntries()) {
                event.accept(regObj.get());

            }
        }
    }
}
