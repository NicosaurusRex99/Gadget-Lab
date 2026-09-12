package nicusha.gadget_lab.data;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.registries.BuiltInRegistries;
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

import java.util.HashSet;
import java.util.Set;

import static nicusha.gadget_lab.GadgetLab.MODID;

public class ModModelProvider extends ModelProvider {
    private final Set<Block> handledBlocks = new HashSet<>();
    private final Set<Item> registeredItems = new HashSet<>();

    public ModModelProvider(PackOutput output) {
        super(output, MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators gen, ItemModelGenerators itemModels) {
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