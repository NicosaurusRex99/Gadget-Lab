package nicusha.gadget_lab.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import nicusha.gadget_lab.GadgetLab;
import nicusha.gadget_lab.registry.ItemRegistry;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AdditionalGen implements DataProvider {
    private final PackOutput packOutput;

    public AdditionalGen(PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    private CompletableFuture<?> generateItemJson(CachedOutput cachedOutput, Identifier itemId, boolean isBlock) {
        JsonObject root = new JsonObject();
        JsonObject modelObject = new JsonObject();
        modelObject.addProperty("type", "minecraft:model");
        String modelPrefix = isBlock ? "block/" : "item/";
        modelObject.addProperty("model", GadgetLab.MODID + ":" + modelPrefix + itemId.getPath());
        root.add("model", modelObject);
        return DataProvider.saveStable(cachedOutput, root, getItemModelPath(itemId));
    }

    private Path getItemModelPath(Identifier itemId) {
        return packOutput.getOutputFolder(PackOutput.Target.RESOURCE_PACK).resolve(GadgetLab.MODID).resolve("items").resolve(itemId.getPath() + ".json");
    }

    private CompletableFuture<?> generateEquipmentJson(CachedOutput cachedOutput, String equipmentId) {
        JsonObject root = new JsonObject();
        JsonObject layers = new JsonObject();
        JsonArray humanoidLayerArray = new JsonArray();
        JsonObject humanoidLayer = new JsonObject();
        humanoidLayer.addProperty("texture", GadgetLab.MODID + ":" + equipmentId);
        humanoidLayerArray.add(humanoidLayer);
        layers.add("humanoid", humanoidLayerArray);
        JsonArray humanoidLeggingsLayerArray = new JsonArray();
        JsonObject humanoidLeggingsLayer = new JsonObject();
        humanoidLeggingsLayer.addProperty("texture", GadgetLab.MODID + ":" + equipmentId);
        humanoidLeggingsLayerArray.add(humanoidLeggingsLayer);
        layers.add("humanoid_leggings", humanoidLeggingsLayerArray);
        root.add("layers", layers);
        return DataProvider.saveStable(cachedOutput, root, getEquipmentModelPath(equipmentId));
    }

    private Path getEquipmentModelPath(String equipmentId) {
        return packOutput.getOutputFolder(PackOutput.Target.RESOURCE_PACK).resolve(GadgetLab.MODID).resolve("equipment").resolve(equipmentId + ".json");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        List<CompletableFuture<?>> futures = new ArrayList<>();
        for (var regObj : ItemRegistry.ITEMS.getEntries()) {
            Item item = regObj.get();
            boolean isBlock = Block.byItem(item) != Blocks.AIR;

            futures.add(generateItemJson(cachedOutput, regObj.getId(), isBlock));
        }
        futures.add(generateEquipmentJson(cachedOutput, "gravity_boots"));
        futures.add(generateEquipmentJson(cachedOutput, "rebreather"));
        futures.add(generateEquipmentJson(cachedOutput, "invisibility_cloak"));

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "gen additional data";
    }
}