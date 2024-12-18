package nicusha.gadget_lab.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import nicusha.gadget_lab.GadgetLab;
import nicusha.gadget_lab.registry.ItemRegistry;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CompletableFuture;

public class AdditionalGen implements DataProvider {
    private PackOutput packOutput;

    public AdditionalGen(PackOutput packOutput) {
        super();
        this.packOutput = packOutput;
    }

    private void generateItemJson(ResourceLocation itemId) {
        JsonObject root = new JsonObject();

        JsonObject modelObject = new JsonObject();
        modelObject.addProperty("type", "minecraft:model");
        modelObject.addProperty("model", GadgetLab.MODID + ":" + (itemId.getPath().startsWith("block/") ? "block/" : "item/") + itemId.getPath());
        root.add("model", modelObject);


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonContent = gson.toJson(root);

        Path path = getItemModelPath(itemId);
        Path parentDir = path.getParent();
        if (!Files.exists(parentDir)) {
            try {
                Files.createDirectories(parentDir);
            } catch (IOException e) {
                return;
            }
        }

        try {
            Files.write(path, jsonContent.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Path getItemModelPath(ResourceLocation itemId) {
        return packOutput.getOutputFolder(PackOutput.Target.RESOURCE_PACK)
                .resolve(GadgetLab.MODID)
                .resolve("items")
                .resolve(itemId.getPath() + ".json");
    }

    private void generateEquipmentJson(String equipmentId) {
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
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonContent = gson.toJson(root);
        Path path = getEquipmentModelPath(equipmentId);
        Path parentDir = path.getParent();
        if (!Files.exists(parentDir)) {
            try {
                Files.createDirectories(parentDir);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        try {
            Files.write(path, jsonContent.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Path getEquipmentModelPath(String equipmentId) {
        return packOutput.getOutputFolder(PackOutput.Target.RESOURCE_PACK)
                .resolve(GadgetLab.MODID)
                .resolve("equipment")
                .resolve(equipmentId + ".json");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        for (var regObj : ItemRegistry.ITEMS.getEntries()) {
            generateItemJson(regObj.getId());
        }

        generateEquipmentJson("gravity_boots");
        generateEquipmentJson("rebreather");
        generateEquipmentJson("invisibility_cloak");
        return null;
    }

    @Override
    public String getName() {
        return "gen additional data";
    }
}