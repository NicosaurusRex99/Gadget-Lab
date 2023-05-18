package nicusha.gadget_lab.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.registries.*;
import nicusha.gadget_lab.Main;
import nicusha.gadget_lab.registry.*;

public class Tab {
    public static final ResourceLocation TOOLS = new ResourceLocation(Main.MODID, "gadget_lab");

    public static void registerTabs(CreativeModeTabEvent.Register event){
        event.registerCreativeModeTab(TOOLS, builder -> builder.title(Component.translatable("itemGroup.gadget_lab")).icon(() -> new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Main.MODID, "rebreather")))).displayItems((flags, output) -> {
            for(RegistryObject<Item> item : ItemRegistry.ITEMS.getEntries()) {
               output.accept(item.get());
            }
        }));
        }
}