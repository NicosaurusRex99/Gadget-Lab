package nicusha.gadget_lab.data.recipe.runner;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import nicusha.gadget_lab.data.recipe.CraftingGen;

import java.util.concurrent.CompletableFuture;

public class CraftingGeneratorRunner extends RecipeProvider.Runner {

    public CraftingGeneratorRunner(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        return new CraftingGen(output, provider);
    }

    @Override
    public String getName() {
        return "Gadget Lab Recipes";
    }
}