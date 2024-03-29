package nicusha.gadget_lab.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import nicusha.gadget_lab.block_entities.PedestalBlockEntity;

public class PedestalBlockRenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {
    private final TextureManager textureManager;

    public PedestalBlockRenderer(BlockEntityRendererProvider.Context context) {
        this.textureManager = context.getEntityRenderer().textureManager;
    }

    @Override
    public void render(T tileEntity, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        ItemStack itemStack = ((PedestalBlockEntity) tileEntity).getItem();
        if (!itemStack.isEmpty()) {
            matrixStack.pushPose();
            matrixStack.translate(0.5, 1.0, 0.5);

            renderItem(itemStack, matrixStack, buffer, combinedLight, combinedOverlay, tileEntity);

            matrixStack.popPose();
        }
    }

    private void renderItem(ItemStack itemStack, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay, BlockEntity blockEntity) {
        Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemDisplayContext.GROUND, combinedLight, combinedOverlay, matrixStack, buffer, blockEntity.getLevel(), 0);
    }
}
