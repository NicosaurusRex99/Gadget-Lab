package nicusha.gadget_lab.data.tag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import nicusha.gadget_lab.GadgetLab;
import nicusha.gadget_lab.registry.BlockRegistry;
import nicusha.gadget_lab.registry.ItemRegistry;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public static final TagKey<Item> SLIME_REPAIR = create(GadgetLab.MODID, "slime_ball_repair");
    public static final TagKey<Item> AIR_REPAIR = create(GadgetLab.MODID, "air_repair");
    public static final TagKey<Item> CHARM = create("curious", "charm");
    public static final TagKey<Item> HANDS = create("curious", "hands");
    public static final TagKey<Item> NECKLACE = create("curious", "necklace");
    public static final TagKey<Item> FOOT_ARMOR = create("minecraft", "enchantable/foot_armor");
    public static final TagKey<Item> SAND = create("minecraft", "sand");
    public static final TagKey<Item> TRIMMABLE_ARMOR = create("minecraft", "trimmable_armor");

    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future) {
        super(output, future, GadgetLab.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(SLIME_REPAIR).add(Items.SLIME_BALL.builtInRegistryHolder().key()).addTag(ItemTags.create(Identifier.fromNamespaceAndPath("c", "slime_balls")));
        tag(AIR_REPAIR).add(Items.PAPER.builtInRegistryHolder().key(), Items.CHARCOAL.builtInRegistryHolder().key()).addTag(ItemTags.WOOL);
        tag(CHARM).add(ItemRegistry.lucky_amulet.key(), ItemRegistry.pocket_watch.key());
        tag(HANDS).add(ItemRegistry.magnetic_glove.key());
        tag(NECKLACE).add(ItemRegistry.lucky_amulet.key());
        tag(FOOT_ARMOR).add(ItemRegistry.gravity_boots.key());
        tag(SAND).add(Item.byBlock(BlockRegistry.quicksand.get()).builtInRegistryHolder().key());
        tag(TRIMMABLE_ARMOR).add(ItemRegistry.gravity_boots.key());
        tag(ItemTags.SWORDS).add(ItemRegistry.copper_sword.key(), ItemRegistry.diamond_sword.key(), ItemRegistry.golden_sword.key(), ItemRegistry.iron_sword.key(), ItemRegistry.netherite_sword.key(), ItemRegistry.stone_sword.key(), ItemRegistry.wood_sword.key());
        tag(ItemTags.MELEE_WEAPON_ENCHANTABLE).add(ItemRegistry.copper_sword.key(), ItemRegistry.diamond_sword.key(), ItemRegistry.golden_sword.key(), ItemRegistry.iron_sword.key(), ItemRegistry.netherite_sword.key(), ItemRegistry.stone_sword.key(), ItemRegistry.wood_sword.key());
        tag(ItemTags.SHARP_WEAPON_ENCHANTABLE).add(ItemRegistry.copper_sword.key(), ItemRegistry.diamond_sword.key(), ItemRegistry.golden_sword.key(), ItemRegistry.iron_sword.key(), ItemRegistry.netherite_sword.key(), ItemRegistry.stone_sword.key(), ItemRegistry.wood_sword.key());
        tag(ItemTags.WEAPON_ENCHANTABLE).add(ItemRegistry.copper_sword.key(), ItemRegistry.diamond_sword.key(), ItemRegistry.golden_sword.key(), ItemRegistry.iron_sword.key(), ItemRegistry.netherite_sword.key(), ItemRegistry.stone_sword.key(), ItemRegistry.wood_sword.key());

    }


    private static TagKey<Item> create(String modid, String tagName) {
        return ItemTags.create(Identifier.fromNamespaceAndPath(modid, tagName));
    }
}
