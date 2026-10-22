package nicusha.gadget_lab.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import nicusha.gadget_lab.blocks.LaunchPad;
import nicusha.gadget_lab.blocks.Pedestal;
import nicusha.gadget_lab.blocks.UnstableObsidian;
import nicusha.gadget_lab.registry.BlockRegistry;
import nicusha.gadget_lab.registry.ItemRegistry;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static nicusha.gadget_lab.GadgetLab.MODID;

public class ModModelProvider extends ModelProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final Set<Block> handledBlocks = new HashSet<>();
    private final Set<Item> registeredItems = new HashSet<>();
    private final PackOutput packOutput;

    public ModModelProvider(PackOutput output) {
        super(output, MODID);
        this.packOutput = output;
    }

    @Override
    protected void registerModels(BlockModelGenerators gen, ItemModelGenerators itemModels) {
        saveCustomBlockModels();

        for (var entry : BlockRegistry.BLOCKS.getEntries()) {
            Block block = entry.get();
            if (handledBlocks.contains(block)) continue;
            handledBlocks.add(block);
            Item blockItem = block.asItem();
            if (block instanceof LaunchPad || block instanceof Pedestal || block instanceof UnstableObsidian) {
                registerCustomParentBlock(gen, block);
            } else {
                gen.createTrivialCube(block);
            }

            if (blockItem != Items.AIR) {
                registeredItems.add(blockItem);
            }
        }
        for (var entry : ItemRegistry.ITEMS.getEntries()) {
            Item item = entry.get();
            if (registeredItems.contains(item) || Block.byItem(item) != Blocks.AIR) {
                continue;
            }
            if (isHandheldTool(item)) {
                itemModels.generateFlatItem(item, ModelTemplates.FLAT_HANDHELD_ITEM);
            } else {
                itemModels.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
            }
            registeredItems.add(item);
        }
    }

    private void saveCustomBlockModels() {
        String launchPadJson = """
        {
            "credit": "Made with Blockbench",
            "textures": {
               "0": "minecraft:block/iron_block",
               "1": "minecraft:block/slime_block",
               "2": "minecraft:block/redstone_block",
               "particle": "minecraft:block/iron_block"
            },
            "elements": [
               {
                  "name": "Base",
                  "from": [2, 0, 2],
                  "to": [14, 1, 14],
                  "faces": {
                     "north": {"uv": [2, 0, 14, 1], "texture": "#0"},
                     "east": {"uv": [2, 0, 14, 1], "texture": "#0"},
                     "south": {"uv": [2, 0, 14, 1], "texture": "#0"},
                     "west": {"uv": [2, 0, 14, 1], "texture": "#0"},
                     "up": {"uv": [2, 2, 14, 14], "texture": "#0"},
                     "down": {"uv": [2, 2, 14, 14], "texture": "#0"}
                  }
               },
               {
                  "name": "Pad",
                  "from": [5, 1, 5],
                  "to": [11, 2, 11],
                  "faces": {
                     "north": {"uv": [5, 5, 11, 6], "texture": "#1"},
                     "east": {"uv": [5, 5, 11, 6], "texture": "#1"},
                     "south": {"uv": [5, 5, 11, 6], "texture": "#1"},
                     "west": {"uv": [5, 5, 11, 6], "texture": "#1"},
                     "up": {"uv": [5, 5, 11, 11], "texture": "#1"},
                     "down": {"uv": [5, 5, 11, 11], "texture": "#1"}
                  }
               },
               {
                  "name": "Rim",
                  "from": [4, 1, 4],
                  "to": [12, 1.5, 12],
                  "faces": {
                     "north": {"uv": [5, 5, 11, 6], "texture": "#2"},
                     "east": {"uv": [5, 5, 11, 6], "texture": "#2"},
                     "south": {"uv": [5, 5, 11, 6], "texture": "#2"},
                     "west": {"uv": [5, 5, 11, 6], "texture": "#2"},
                     "up": {"uv": [5, 5, 11, 11], "texture": "#2"},
                     "down": {"uv": [5, 5, 11, 11], "texture": "#2"}
                  }
               }
            ]
        }
        """;

        String pedestalJson = """
        {
            "render_type": "minecraft:cutout_mipped",
            "textures": {
               "0": "minecraft:block/basalt_side",
               "1": "minecraft:block/basalt_top",
               "2": "minecraft:block/red_wool",
               "particle": "minecraft:block/basalt_side"
            },
            "elements": [
               {
                  "name": "Base",
                  "from": [2, 0, 2],
                  "to": [14, 2, 14],
                  "faces": {
                     "north": {"uv": [2, 0, 14, 2], "texture": "#0"},
                     "east": {"uv": [2, 0, 14, 2], "texture": "#0"},
                     "south": {"uv": [2, 0, 14, 2], "texture": "#0"},
                     "west": {"uv": [2, 0, 14, 2], "texture": "#0"},
                     "up": {"uv": [2, 2, 14, 14], "texture": "#1"},
                     "down": {"uv": [2, 2, 14, 14], "texture": "#1"}
                  }
               },
               {
                  "name": "Collumn",
                  "from": [6, 0.1, 6],
                  "to": [10, 12, 10],
                  "faces": {
                     "north": {"uv": [6, 0, 10, 12], "texture": "#0"},
                     "east": {"uv": [6, 0, 10, 12], "texture": "#0"},
                     "south": {"uv": [6, 0, 10, 12], "texture": "#0"},
                     "west": {"uv": [6, 0, 10, 12], "texture": "#0"},
                     "up": {"uv": [6, 5, 10, 9], "texture": "#1"},
                     "down": {"uv": [6, 5, 10, 9], "texture": "#1"}
                  }
               },
               {
                  "name": "Top",
                  "from": [4, 11, 4],
                  "to": [12, 13, 12],
                  "faces": {
                     "north": {"uv": [4, 2, 12, 4], "texture": "#0"},
                     "east": {"uv": [4, 2, 12, 4], "texture": "#0"},
                     "south": {"uv": [4, 2, 12, 4], "texture": "#0"},
                     "west": {"uv": [4, 2, 12, 4], "texture": "#0"},
                     "up": {"uv": [4, 3, 12, 11], "texture": "#1"},
                     "down": {"uv": [4, 3, 12, 11], "texture": "#1"}
                  }
               },
               {
                  "name": "Pillow",
                  "from": [6, 12, 6],
                  "to": [10, 14, 10],
                  "faces": {
                     "north": {"uv": [5, 2, 9, 4], "texture": "#2"},
                     "east": {"uv": [5, 2, 9, 4], "texture": "#2"},
                     "south": {"uv": [5, 2, 9, 4], "texture": "#2"},
                     "west": {"uv": [5, 2, 9, 4], "texture": "#2"},
                     "up": {"uv": [6, 8, 10, 12], "texture": "#2"},
                     "down": {"uv": [6, 8, 10, 12], "texture": "#2"}
                  }
               }
            ]
        }
        """;

        String unstableObsidianJson = """
        {
          "parent": "minecraft:block/cube_all",
          "textures": {
            "all": "minecraft:block/obsidian"
          }
        }
        """;
        try {
            CachedOutput cache = CachedOutput.NO_CACHE;
            writeModelJson("launch_pad", launchPadJson);
            writeModelJson("pedestal", pedestalJson);
            writeModelJson("unstable_obsidian", unstableObsidianJson);
        } catch (Exception ignored) {}
    }

    private void writeModelJson(String name, String jsonContent) {
        try {
            Path path = packOutput.getOutputFolder(PackOutput.Target.RESOURCE_PACK)
                    .resolve(MODID)
                    .resolve("models")
                    .resolve("block")
                    .resolve(name + ".json");
            JsonObject jsonObject = JsonParser.parseString(jsonContent).getAsJsonObject();
            // Using standard Java files write since model provider runs synchronously during asset generation
            java.nio.file.Files.createDirectories(path.getParent());
            java.nio.file.Files.writeString(path, GSON.toJson(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isHandheldTool(Item item) {
        String path = BuiltInRegistries.ITEM.getKey(item).getPath();
        return path.endsWith("_sword") || path.endsWith("_pickaxe") || path.endsWith("_axe") || path.endsWith("_shovel") || path.endsWith("_hoe") || path.contains("hammer") || path.contains("wand") || path.contains("staff");
    }

    private void registerCustomParentBlock(BlockModelGenerators gen, Block block) {
        String path = BuiltInRegistries.BLOCK.getKey(block).getPath();
        Identifier modelId = Identifier.fromNamespaceAndPath(MODID, "block/" + path);
        gen.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, BlockModelGenerators.plainVariant(modelId)));
        Item blockItem = block.asItem();
        if (blockItem != Items.AIR) {
            gen.registerSimpleItemModel(blockItem, modelId);
        }
    }
}