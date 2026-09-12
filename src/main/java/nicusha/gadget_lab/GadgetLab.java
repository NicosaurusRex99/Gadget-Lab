package nicusha.gadget_lab;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.*;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import nicusha.gadget_lab.events.*;
import nicusha.gadget_lab.registry.*;
import org.slf4j.Logger;

@Mod(GadgetLab.MODID)
public class GadgetLab
{
    public static final String MODID = "gadget_lab";
    public static final Logger LOGGER = LogUtils.getLogger();

    public GadgetLab(IEventBus bus, ModContainer container) {
        bus.addListener(this::common);
        bus.addListener(this::client);

        BlockRegistry.BLOCKS.register(bus);
        ItemRegistry.ITEMS.register(bus);
        BlockRegistry.BLOCK_ENTITIES.register(bus);
        EnchantmentRegistry.ENCHANTMENTS.register(bus);
        EntityRegistry.ENTITIES.register(bus);
        CreativeTabRegistry.TABS.register(bus);
        DataComponentRegistry.COMPONENTS.register(bus);
        SoundRegistry.SOUNDS.register(bus);

        bus.addListener(this::addCreative);
        NeoForge.EVENT_BUS.register(this);
        bus.addListener(EntityRegistry::registerRenderers);

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

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }
}