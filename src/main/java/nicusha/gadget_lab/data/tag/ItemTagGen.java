package nicusha.gadget_lab.data.tag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import nicusha.gadget_lab.GadgetLab;
import nicusha.gadget_lab.registry.BlockRegistry;
import nicusha.gadget_lab.registry.ItemRegistry;

import java.util.concurrent.CompletableFuture;

public class ItemTagGen extends ItemTagsProvider {
    public static final TagKey<Item> SLIME_REPAIR = create(GadgetLab.MODID, "slime_ball_repair");
    public static final TagKey<Item> AIR_REPAIR = create(GadgetLab.MODID, "air_repair");
    public static final TagKey<Item> CHARM = create("curious", "charm");
    public static final TagKey<Item> HANDS = create("curious", "hands");
    public static final TagKey<Item> NECKLACE = create("curious", "necklace");
    public static final TagKey<Item> FOOT_ARMOR = create("minecraft", "enchantable/foot_armor");
    public static final TagKey<Item> SAND = create("minecraft", "sand");
    public static final TagKey<Item> TRIMMABLE_ARMOR = create("minecraft", "trimmable_armor");

    public ItemTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> future, CompletableFuture<TagLookup<Block>> provider, ExistingFileHelper helper) {
        super(output, future, provider, GadgetLab.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(SLIME_REPAIR).add(Items.SLIME_BALL).addTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "slime_balls")));
        tag(AIR_REPAIR).add(Items.PAPER, Items.CHARCOAL).addTag(ItemTags.WOOL);
        tag(CHARM).add(ItemRegistry.lucky_amulet.get(), ItemRegistry.pocket_watch.get());
        tag(HANDS).add(ItemRegistry.magnetic_glove.get());
        tag(NECKLACE).add(ItemRegistry.lucky_amulet.get());
        tag(FOOT_ARMOR).add(ItemRegistry.gravity_boots.get());
        tag(SAND).add(Item.byBlock(BlockRegistry.quicksand.get()));
        tag(TRIMMABLE_ARMOR).add(ItemRegistry.gravity_boots.get());
    }


    private static TagKey<Item> create(String modid, String tagName) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(modid, tagName));
    }
}
