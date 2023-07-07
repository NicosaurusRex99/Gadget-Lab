package nicusha.gadget_lab.registry;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.*;
import nicusha.gadget_lab.items.*;

import java.util.function.Supplier;

import static nicusha.gadget_lab.Main.MODID;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final RegistryObject<Item> rebreather = registerItem("rebreather", () -> new Rebreather());
    public static final RegistryObject<Item> portable_crafting_table = registerItem("portable_crafting_table", () -> new PortableCraftingTable());
    public static final RegistryObject<Item> enigmatic_hold = registerItem("enigmatic_hold", () -> new EnigmaticHold());
    public static final RegistryObject<Item> pocket_watch = registerItem("pocket_watch", () -> new ItemMod(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> gravity_boots = registerItem("gravity_boots", () -> new GravityBoots());
    public static final RegistryObject<Item> fortune_amulet = registerItem("fortune_amulet", () -> new FortuneAmulet());
    public static final RegistryObject<Item> teleportation_wand = registerItem("teleportation_wand", () -> new TeleportationWand());


    private static <T extends Item> RegistryObject<T> registerItem(String registryId, Supplier<T> item) {
        return ITEMS.register(registryId, item);
    }
}
