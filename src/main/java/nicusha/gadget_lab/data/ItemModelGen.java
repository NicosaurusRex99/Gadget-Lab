package nicusha.gadget_lab.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import nicusha.gadget_lab.GadgetLab;
import nicusha.gadget_lab.blocks.LaunchPad;
import nicusha.gadget_lab.blocks.Pedestal;
import nicusha.gadget_lab.blocks.UnstableObsidian;
import nicusha.gadget_lab.items.ItemMod;
import nicusha.gadget_lab.registry.BlockRegistry;
import nicusha.gadget_lab.registry.ItemRegistry;

public class ItemModelGen extends net.neoforged.neoforge.client.model.generators.ItemModelProvider {
    private PackOutput packOutput;
    public ItemModelGen(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, GadgetLab.MODID, existingFileHelper);
        this.packOutput = packOutput;
    }

    @Override
    protected void registerModels() {
        for (var regObj : ItemRegistry.ITEMS.getEntries()) {
            if (regObj.get() instanceof ItemMod || regObj.get() instanceof ArmorItem) {
                if (!regObj.value().equals(BlockRegistry.launch_pad) && !regObj.value().equals(BlockRegistry.PEDESTAL) && !regObj.value().equals(BlockRegistry.unstable_obsidian)) {
                    generated(regObj.getId());
                }
            }
            if (regObj.get() instanceof SwordItem || regObj.get() instanceof ShovelItem || regObj.get() instanceof HoeItem || regObj.get() instanceof AxeItem || regObj.get() instanceof PickaxeItem) {
                handheld(regObj.getId());
            }
        }
        for (var regObj : BlockRegistry.BLOCKS.getEntries()) {
            if (regObj.get() instanceof Block) {
                if ((!(regObj.get() instanceof LaunchPad)) && (!(regObj.get() instanceof Pedestal)) && (!(regObj.get() instanceof UnstableObsidian))) {
                    simpleBlock(regObj.getId());
                    simpleBlockReg(regObj.getId());
                }
            }
        }
        customParent("launch_pad", "block/launch_pad");
        customParent("unstable_obsidian", "block/unstable_obsidian");
        customParent("pedestal", "block/pedestal");
    }

    private ItemModelBuilder generated(ResourceLocation itemId) {
        return withExistingParent("item/" + itemId.getPath(), ResourceLocation.parse("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(GadgetLab.MODID, "item/" + itemId.getPath()));
    }

    private ItemModelBuilder handheld(ResourceLocation itemId) {
        return withExistingParent("item/" + itemId.getPath(), ResourceLocation.parse("item/handheld"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(GadgetLab.MODID, "item/" + itemId.getPath()));
    }

    private ItemModelBuilder custom(String item, String parentModel, String texture) {
        return withExistingParent("item/" + item, ResourceLocation.parse(parentModel))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(GadgetLab.MODID, "item/" + texture));
    }

    private ItemModelBuilder customParent(String item, String parentModel) {
        return withExistingParent("item/" + item, ResourceLocation.fromNamespaceAndPath(GadgetLab.MODID, parentModel));
    }

    private ItemModelBuilder simpleBlock(ResourceLocation itemId) {
        return withExistingParent("block/" + itemId.getPath(), ResourceLocation.parse("block/cube_all"))
                .texture("all", ResourceLocation.fromNamespaceAndPath(GadgetLab.MODID, "block/" + itemId.getPath()));
    }

    private ItemModelBuilder simpleBlockReg(ResourceLocation itemId) {
        return withExistingParent("item/" + itemId.getPath(), ResourceLocation.parse("block/cube_all"))
                .texture("all", ResourceLocation.fromNamespaceAndPath(GadgetLab.MODID, "block/" + itemId.getPath()));
    }

}
