package nicusha.gadget_lab.data.recipe;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceKey;
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
import nicusha.gadget_lab.GadgetLab;
import nicusha.gadget_lab.registry.BlockRegistry;
import nicusha.gadget_lab.registry.ItemRegistry;

public class CraftingGen extends RecipeProvider {
    private final HolderGetter<Item> itemGetter;

    public CraftingGen(RecipeOutput recipeOutput, HolderLookup.Provider holderProvider) {
        super(holderProvider, recipeOutput);
        this.itemGetter = holderProvider.lookupOrThrow(Registries.ITEM);
    }

    @Override
    protected void buildRecipes() {
        smelting(BlockRegistry.quicksand, Blocks.SAND, 0.2f, 200);
        shaped(ItemRegistry.enigmatic_hold.get(), 1, new String[]{"#G#","OSO","#G#"},'#', Items.IRON_BARS,'G', Items.GHAST_TEAR, 'O', Items.GLOWSTONE_DUST, 'S', Items.STONE_BUTTON);
        shaped(ItemRegistry.gravity_boots.get(), 1, new String[]{"#S#","#P#"},'#', Items.WOOL.magenta(),'S', Items.SLIME_BLOCK, 'P', Items.PISTON);
        shaped(ItemRegistry.herbicide_spray.get(), 1, new String[]{"#","R", "B"},'#', Items.TRIPWIRE_HOOK,'R', Items.ROTTEN_FLESH, 'B', Items.BUCKET);
        shaped(BlockRegistry.launch_pad.get(), 8, new String[]{"ISI", "RPR", "ICI"},'I', Items.IRON_INGOT,'S', Items.SLIME_BLOCK, 'R', Items.REDSTONE, 'P', Items.STICKY_PISTON, 'C', Items.COMPARATOR);
        shaped(ItemRegistry.lucky_amulet.get(), 1, new String[]{"# #"," # "," H "},'#', Items.IRON_CHAIN,'H', Items.HEART_OF_THE_SEA);
        shaped(ItemRegistry.magnetic_glove.get(), 1, new String[]{"#I ","iii"," iB"},'#', Items.DYE.red(),'I', Items.IRON_NUGGET, 'i', Items.IRON_INGOT, 'B', Items.DYE.blue());
        shaped(BlockRegistry.pedestal.get(), 1, new String[]{" # ", " B ", "BBB"},'#', Items.WOOL.red(),'B', Items.BASALT);
        shaped(ItemRegistry.portable_crafting_table.get(), 1, new String[]{" # ","#C#"," # "},'#', ItemTags.PLANKS,'C', Items.CRAFTING_TABLE);
        shaped(ItemRegistry.rebreather.get(), 1, new String[]{" C ","CWC"},'C', Items.CHARCOAL,'W', Items.WOOL.white());
        shaped(ItemRegistry.smoke_bomb.get(), 3, new String[]{"#S#","#C#","###"},'#', Items.DYED_TERRACOTTA.green(),'S', Items.STONE_PRESSURE_PLATE, 'C', Items.CAMPFIRE);
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

    protected ResourceKey<Recipe<?>> createRecipeKey(String name) {
        return ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(GadgetLab.MODID, name));
    }

    protected final void shaped(ItemLike result, int count, String[] pattern, Object... keys) {
        ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(itemGetter, RecipeCategory.MISC, result, count);
        for (String line : pattern) {
            builder.pattern(line);
        }
        for (int i = 0; i < keys.length; i += 2) {
            Object key = keys[i];
            Object value = keys[i + 1];
            if (value instanceof ItemLike) {
                builder.define((Character) key, (ItemLike) value);
            } else if (value instanceof TagKey) {
                builder.define((Character) key, (TagKey<Item>) value);
            } else if (value instanceof Ingredient) {
                builder.define((Character) key, (Ingredient) value);
            } else {
                throw new IllegalArgumentException("Invalid ingredient type: " + value.getClass().getSimpleName());
            }
        }
        Object firstIngredient = keys[1];
        if (firstIngredient instanceof ItemLike) {
            builder.unlockedBy("has_item", has((ItemLike) firstIngredient));
        } else if (firstIngredient instanceof TagKey) {
            builder.unlockedBy("has_item", has((TagKey<Item>) firstIngredient));
        } else if (firstIngredient instanceof Ingredient) {
            builder.unlockedBy("has_item", has(((Ingredient) firstIngredient).items().findFirst().get().value()));
        }
        builder.save(this.output, createRecipeKey("shaped/" + BuiltInRegistries.ITEM.getKey(result.asItem()).getPath()));
    }

    protected final void shapeless(ItemLike result, int count, Object... ingredients) {
        ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(itemGetter, RecipeCategory.MISC, result, count);
        for (Object ingredient : ingredients) {
            if (ingredient instanceof ItemLike) {
                builder.requires((ItemLike) ingredient);
            } else if (ingredient instanceof TagKey) {
                builder.requires((TagKey<Item>) ingredient);
            } else {
                throw new IllegalArgumentException("Ingredient must be either ItemLike or TagKey<Item>");
            }
        }
        if (ingredients[0] instanceof ItemLike) {
            builder.unlockedBy("has_item", has((ItemLike) ingredients[0]));
        } else if (ingredients[0] instanceof TagKey) {
            builder.unlockedBy("has_item", has(((TagKey<Item>) ingredients[0])));
        }
        builder.save(this.output, createRecipeKey("shapeless/" + BuiltInRegistries.ITEM.getKey(result.asItem()).getPath()));
    }

    protected final void shapeless(ItemLike result, String group, int count, Object... ingredients) {
        ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(itemGetter, RecipeCategory.MISC, result, count);
        for (Object ingredient : ingredients) {
            if (ingredient instanceof ItemLike) {
                builder.requires((ItemLike) ingredient);
            } else if (ingredient instanceof TagKey) {
                builder.requires((TagKey<Item>) ingredient);
            } else {
                throw new IllegalArgumentException("Ingredient must be either ItemLike or TagKey<Item>");
            }
        }
        builder.group(group);
        if (ingredients[0] instanceof ItemLike) {
            builder.unlockedBy("has_item", has((ItemLike) ingredients[0]));
        } else if (ingredients[0] instanceof TagKey) {
            builder.unlockedBy("has_item", has(((TagKey<Item>) ingredients[0])));
        }
        builder.save(this.output, createRecipeKey("shapeless/" + BuiltInRegistries.ITEM.getKey(result.asItem()).getPath()));
    }

    protected final void smelting(ItemLike input, ItemLike result, float experience, int cookingTime) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), RecipeCategory.MISC, CookingBookCategory.MISC, result, experience, cookingTime).unlockedBy("has_item", has(input)).save(this.output, createRecipeKey("smelting/" + BuiltInRegistries.ITEM.getKey(result.asItem()).getPath()));
    }

}