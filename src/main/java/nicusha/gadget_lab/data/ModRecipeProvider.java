package nicusha.gadget_lab.data;

import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.*;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import nicusha.gadget_lab.registry.BlockRegistry;
import nicusha.gadget_lab.registry.ItemRegistry;

import static nicusha.gadget_lab.GadgetLab.MODID;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
    }

    @Override
    protected void buildRecipes() {
        smelting(BlockRegistry.quicksand, Blocks.SAND, 0.2f);
        shaped(ItemRegistry.enigmatic_hold.get(), 1, new String[]{"#G#","OSO","#G#"},'#', Items.IRON_BARS,'G', Items.GHAST_TEAR, 'O', Items.GLOWSTONE_DUST, 'S', Items.STONE_BUTTON);
        shaped(ItemRegistry.gravity_boots.get(), 1, new String[]{"#S#","#P#"},'#', Items.WOOL.magenta(),'S', Items.SLIME_BLOCK, 'P', Items.PISTON);
        shaped(ItemRegistry.herbicide_spray.get(), 1, new String[]{"#","R", "B"},'#', Items.TRIPWIRE_HOOK,'R', Items.ROTTEN_FLESH, 'B', Items.BUCKET);
        shaped(BlockRegistry.launch_pad.get(), 8, new String[]{"ISI", "RPR", "ICI"},'I', Items.IRON_INGOT,'S', Items.SLIME_BLOCK, 'R', Items.REDSTONE, 'P', Items.STICKY_PISTON, 'C', Items.COMPARATOR);
        shaped(ItemRegistry.lucky_amulet.get(), 1, new String[]{"# #"," # "," H "},'#', Items.IRON_CHAIN,'H', Items.HEART_OF_THE_SEA);
        shaped(ItemRegistry.magnetic_glove.get(), 1, new String[]{"#I ","iii"," iB"},'#', Items.DYE.red(),'I', Items.IRON_NUGGET, 'i', Items.IRON_INGOT, 'B', Items.DYE.blue());
        shaped(BlockRegistry.pedestal.get(), 1, new String[]{" # ", " B ", "BBB"},'#', Items.WOOL.red(),'B', Blocks.BASALT);
        shaped(ItemRegistry.portable_crafting_table.get(), 1, new String[]{" # ","#C#"," # "},'#', ItemTags.PLANKS,'C', Items.CRAFTING_TABLE);
        shaped(ItemRegistry.rebreather.get(), 1, new String[]{" C ","CWC"},'C', Items.CHARCOAL,'W', Items.WOOL.white());
        shaped(ItemRegistry.smoke_bomb.get(), 3, new String[]{"#S#","#C#","###"},'#', Items.DYED_TERRACOTTA.lime(),'S', Items.STONE_PRESSURE_PLATE, 'C', Items.CAMPFIRE);
        shaped(ItemRegistry.teleportation_wand.get(), 1, new String[]{" #E"," /#","/  "},'#', Items.ENDER_PEARL,'E', Items.ENDER_EYE, '/', Items.BLAZE_ROD);

        shapeless(BlockRegistry.black.get(), "solid_blocks", 1, ItemTags.STONE_CRAFTING_MATERIALS, Items.DYE.black());
        shapeless(BlockRegistry.blue.get(), "solid_blocks", 1, ItemTags.STONE_CRAFTING_MATERIALS, Items.DYE.blue());
        shapeless(BlockRegistry.brown.get(), "solid_blocks", 1, ItemTags.STONE_CRAFTING_MATERIALS, Items.DYE.brown());
        shapeless(BlockRegistry.cyan.get(), "solid_blocks", 1, ItemTags.STONE_CRAFTING_MATERIALS, Items.DYE.cyan());
        shapeless(BlockRegistry.gray.get(), "solid_blocks", 1, ItemTags.STONE_CRAFTING_MATERIALS, Items.DYE.gray());
        shapeless(BlockRegistry.green.get(), "solid_blocks", 1, ItemTags.STONE_CRAFTING_MATERIALS, Items.DYE.green());
        shapeless(BlockRegistry.light_blue.get(), "solid_blocks", 1, ItemTags.STONE_CRAFTING_MATERIALS, Items.DYE.lightBlue());
        shapeless(BlockRegistry.light_gray.get(), "solid_blocks", 1, ItemTags.STONE_CRAFTING_MATERIALS, Items.DYE.lightGray());
        shapeless(BlockRegistry.light_green.get(), "solid_blocks", 1, ItemTags.STONE_CRAFTING_MATERIALS, Items.DYE.lime());
        shapeless(BlockRegistry.magenta.get(), "solid_blocks", 1, ItemTags.STONE_CRAFTING_MATERIALS, Items.DYE.magenta());
        shapeless(BlockRegistry.orange.get(), "solid_blocks", 1, ItemTags.STONE_CRAFTING_MATERIALS, Items.DYE.orange());
        shapeless(BlockRegistry.pink.get(), "solid_blocks", 1, ItemTags.STONE_CRAFTING_MATERIALS, Items.DYE.pink());
        shapeless(BlockRegistry.purple.get(), "solid_blocks", 1, ItemTags.STONE_CRAFTING_MATERIALS, Items.DYE.purple());
        shapeless(BlockRegistry.red.get(), "solid_blocks", 1, ItemTags.STONE_CRAFTING_MATERIALS, Items.DYE.red());
        shapeless(BlockRegistry.white.get(), "solid_blocks", 1, ItemTags.STONE_CRAFTING_MATERIALS, Items.DYE.white());
        shapeless(BlockRegistry.yellow.get(), "solid_blocks", 1, ItemTags.STONE_CRAFTING_MATERIALS, Items.DYE.yellow());
        shapeless(ItemRegistry.invisibility_cloak.get(), 1, Items.FERMENTED_SPIDER_EYE, Items.GOLDEN_CARROT, Items.LEATHER_CHESTPLATE, Items.GLASS_BOTTLE);
        shapeless(ItemRegistry.pocket_watch.get(), 1, Items.IRON_CHAIN, Items.CLOCK);
    }

    private void smelting(ItemLike input, ItemLike result, float experience) {
        String path = getItemName(result) + "_from_smelting_" + getItemName(input);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), RecipeCategory.MISC, CookingBookCategory.MISC, result, experience, 200).unlockedBy(getHasName(input), has(input)).save(output, String.valueOf(Identifier.fromNamespaceAndPath(MODID, path)));
    }

    private void shaped(ItemLike result, int count, String[] pattern, Object... keys) {
        shaped(result, count, "", pattern, keys);
    }

    private void shaped(ItemLike result, int count, String pathSuffix, String[] pattern, Object... keys) {
        String defaultPath = getItemName(result) + (count > 1 ? "_" + count : "");
        String path = defaultPath + (pathSuffix.isEmpty() ? "" : "_" + pathSuffix);

        ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, result, count);
        for (String row : pattern) {
            builder.pattern(row);
        }

        ItemLike firstUnlockItem = null;
        for (int i = 0; i < keys.length; i += 2) {
            Character c = (Character) keys[i];
            Object obj = keys[i + 1];
            if (firstUnlockItem == null && obj instanceof ItemLike il) {
                firstUnlockItem = il;
            }

            if (obj instanceof ItemLike il) {
                builder.define(c, il);
            } else if (obj instanceof TagKey<?> tag) {
                @SuppressWarnings("unchecked")
                TagKey<Item> itemTag = (TagKey<Item>) tag;
                builder.define(c, itemTag);
            }
        }

        if (firstUnlockItem != null) {
            builder.unlockedBy(getHasName(firstUnlockItem), has(firstUnlockItem));
        } else {
            builder.unlockedBy("has_item", has(result));
        }

        if (path.equals(defaultPath)) {
            builder.save(output);
        } else {
            builder.save(output, String.valueOf(Identifier.fromNamespaceAndPath(MODID, path)));
        }
    }

    private void shapeless(ItemLike result, int count, Object... ingredients) {
        shapeless(result, "", count, ingredients);
    }

    private void shapeless(ItemLike result, String group, int count, Object... ingredients) {
        String defaultPath = getItemName(result) + (count > 1 ? "_" + count : "");
        String path = defaultPath;

        ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, result, count);
        if (!group.isEmpty()) {
            builder.group(group);
        }

        ItemLike firstUnlockItem = null;
        for (Object ingredient : ingredients) {
            if (ingredient instanceof ItemLike il) {
                if (firstUnlockItem == null) firstUnlockItem = il;
                builder.requires(il);
            } else if (ingredient instanceof TagKey<?> tag) {
                @SuppressWarnings("unchecked")
                TagKey<Item> itemTag = (TagKey<Item>) tag;
                builder.requires(itemTag);
            }
        }

        if (firstUnlockItem != null) {
            builder.unlockedBy(getHasName(firstUnlockItem), has(firstUnlockItem));
        } else {
            builder.unlockedBy("has_item", has(result));
        }

        if (path.equals(defaultPath)) {
            builder.save(output);
        } else {
            builder.save(output, String.valueOf(Identifier.fromNamespaceAndPath(MODID, path)));
        }
    }
}