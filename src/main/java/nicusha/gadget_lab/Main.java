package nicusha.gadget_lab;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import nicusha.gadget_lab.registry.*;
import org.slf4j.Logger;

@Mod(Main.MODID)
public class Main
{
    public static final String MODID = "gadget_lab";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Main(IEventBus bus)
    {
        bus.addListener(this::commonSetup);

        BlockRegistry.BLOCKS.register(bus);
        ItemRegistry.ITEMS.register(bus);
        BlockRegistry.BLOCK_ENTITIES.register(bus);
        EnchantmentRegistry.ENCHANTMENTS.register(bus);
        EntityRegistry.ENTITIES.register(bus);
        CreativeTabRegistry.TABS.register(bus);

//        NeoForge.EVENT_BUS.register(this);
        bus.addListener(this::addCreative);
//        NeoForge.EVENT_BUS.addListener(Rebreather::onPlayerTick);
//        NeoForge.EVENT_BUS.addListener(FortuneAmulet::onPlayerTick);
//        NeoForge.EVENT_BUS.addListener(MagmaWalkerEvent::onEntityMoved);
//        NeoForge.EVENT_BUS.addListener(PocketWatchEvent::renderGameOverlayEvent);
//        NeoForge.EVENT_BUS.addListener(GravityBoots::onPlayerTick);
//        NeoForge.EVENT_BUS.addListener(Rebreather::onPlayerTick);

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeTabRegistry.CREATIVE_TAB.getKey())
            for (var regObj : ItemRegistry.ITEMS.getEntries()) {
                event.accept(regObj.get());
            }
    }

}
