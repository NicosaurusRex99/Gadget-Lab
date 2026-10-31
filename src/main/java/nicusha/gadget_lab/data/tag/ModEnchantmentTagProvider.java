package nicusha.gadget_lab.data.tag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;
import nicusha.gadget_lab.registry.EnchantmentRegistry;

import java.util.concurrent.CompletableFuture;

import static nicusha.gadget_lab.GadgetLab.MODID;

public class ModEnchantmentTagProvider extends EnchantmentTagsProvider {
    public ModEnchantmentTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        tag(EnchantmentTags.NON_TREASURE).add(EnchantmentRegistry.LUMINS);
    }
}
