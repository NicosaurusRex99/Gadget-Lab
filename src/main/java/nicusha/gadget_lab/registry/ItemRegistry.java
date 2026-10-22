package nicusha.gadget_lab.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
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
    public static final DeferredItem<Item> wood_sword = registerSword("reinforced_wooden_sword", new ToolMaterial(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 590, 2.0F, 2.0F, 15, ItemTags.WOODEN_TOOL_MATERIALS));
    public static final DeferredItem<Item> golden_sword = registerSword("reinforced_golden_sword", new ToolMaterial(BlockTags.INCORRECT_FOR_GOLD_TOOL, 320, 12.0F, 2.0F, 22, ItemTags.GOLD_TOOL_MATERIALS));
    public static final DeferredItem<Item> copper_sword = registerSword("reinforced_copper_sword", new ToolMaterial(BlockTags.INCORRECT_FOR_COPPER_TOOL, 1900, 5.0F, 3.0F, 13, ItemTags.COPPER_TOOL_MATERIALS));
    public static final DeferredItem<Item> stone_sword = registerSword("reinforced_stone_sword", new ToolMaterial(BlockTags.INCORRECT_FOR_STONE_TOOL, 1310, 4.0F, 3.0F, 5, ItemTags.STONE_TOOL_MATERIALS));
    public static final DeferredItem<Item> iron_sword = registerSword("reinforced_iron_sword", new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 2500, 6.0F, 4.0F, 14, ItemTags.IRON_TOOL_MATERIALS));
    public static final DeferredItem<Item> diamond_sword = registerSword("reinforced_diamond_sword", new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 15610, 8.0F, 6.0F, 10, ItemTags.DIAMOND_TOOL_MATERIALS));
    public static final DeferredItem<Item> netherite_sword = registerSword("reinforced_netherite_sword", new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 20310, 13.0F, 8.0F, 19, ItemTags.NETHERITE_TOOL_MATERIALS));

    private static <T extends Item> DeferredItem<T> registerItem(String registryId, Supplier<T> item) {
        return ITEMS.register(registryId, item);
    }
    private static DeferredItem<Item> registerSword(String name, ToolMaterial material) {
        return ITEMS.registerItem(name, props -> new Item(props.setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MODID, name))).sword(material, 1.0F, -2.4F)));
    }
}
