package nicusha.gadget_lab.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import nicusha.gadget_lab.GadgetLab;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class RegistryDataGen extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder();

    public RegistryDataGen(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider, BUILDER, Set.of("minecraft", GadgetLab.MODID));
    }
}
