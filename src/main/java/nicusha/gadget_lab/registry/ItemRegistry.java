package nicusha.gadget_lab.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.*;
import nicusha.gadget_lab.items.*;

import java.util.function.Supplier;

import static nicusha.gadget_lab.GadgetLab.MODID;

public class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> rebreather = registerItem("rebreather", () -> new Rebreather());
    public static final DeferredItem<Item> portable_crafting_table = registerItem("portable_crafting_table", () -> new PortableCraftingTable());
    public static final DeferredItem<Item> enigmatic_hold = registerItem("enigmatic_hold", () -> new EnigmaticHold());
    public static final DeferredItem<Item> pocket_watch = registerItem("pocket_watch", () -> new ItemMod(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MODID, "pocket_watch")))));
    public static final DeferredItem<Item> gravity_boots = registerItem("gravity_boots", () -> new GravityBoots());
    public static final DeferredItem<Item> lucky_amulet = registerItem("lucky_amulet", () -> new LuckyAmulet());
    public static final DeferredItem<Item> teleportation_wand = registerItem("teleportation_wand", () -> new TeleportationWand());
    public static final DeferredItem<Item> smoke_bomb = registerItem("smoke_bomb", () -> new SmokeBomb());
    public static final DeferredItem<Item> magnetic_glove = registerItem("magnetic_glove", () -> new MagneticGlove());
    public static final DeferredItem<Item> herbicide_spray = registerItem("herbicide_spray", () -> new HerbicideSpray());
    public static final DeferredItem<Item> invisibility_cloak = registerItem("invisibility_cloak", () -> new InvisibilityCloak());

    private static <T extends Item> DeferredItem<T> registerItem(String registryId, Supplier<T> item) {
        return ITEMS.register(registryId, item);
    }
}
