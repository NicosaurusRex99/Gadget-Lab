package nicusha.gadget_lab;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.*;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import nicusha.gadget_lab.events.*;
import nicusha.gadget_lab.registry.*;
import org.slf4j.Logger;

@Mod(Main.MODID)
public class Main
{
    public static final String MODID = "gadget_lab";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Main(IEventBus bus)
    {
        bus.addListener(this::common);
        bus.addListener(this::client);

        BlockRegistry.BLOCKS.register(bus);
        ItemRegistry.ITEMS.register(bus);
        BlockRegistry.BLOCK_ENTITIES.register(bus);
        EnchantmentRegistry.ENCHANTMENTS.register(bus);
        EntityRegistry.ENTITIES.register(bus);
        CreativeTabRegistry.TABS.register(bus);
        DataComponentRegistry.COMPONENTS.register(bus);

        bus.addListener(this::addCreative);

    }

    private void common(final FMLCommonSetupEvent event)
    {

    }
    private void client(final FMLClientSetupEvent event)
    {
        BlockRegistry.renderTiles();
        NeoForge.EVENT_BUS.register(PocketWatchEvent.class);
    }
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeTabRegistry.CREATIVE_TAB.getKey())
            for (var regObj : ItemRegistry.ITEMS.getEntries()) {
                event.accept(regObj.get());
            }
    }

}
