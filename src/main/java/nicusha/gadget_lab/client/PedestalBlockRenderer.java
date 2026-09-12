package nicusha.gadget_lab.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import nicusha.gadget_lab.block_entities.PedestalBlockEntity;

public class PedestalBlockRenderer implements BlockEntityRenderer<PedestalBlockEntity, PedestalBlockRenderer.PedestalRenderState> {
    private final ItemModelResolver itemModelResolver;

    public PedestalBlockRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    public static class PedestalRenderState extends BlockEntityRenderState {
        public final ItemStackRenderState itemRenderState = new ItemStackRenderState();
        public float gameTime;
    }

    @Override
    public PedestalRenderState createRenderState() {
        return new PedestalRenderState();
    }

    @Override
    public void extractRenderState(PedestalBlockEntity blockEntity, PedestalRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        if (!blockEntity.getItem().isEmpty() && blockEntity.getLevel() != null) {
            this.itemModelResolver.updateForTopItem(state.itemRenderState, blockEntity.getItem(), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 0);
            state.lightCoords = blockEntity.getLevel().getLightEmission(blockEntity.getBlockPos().above());
            state.gameTime = blockEntity.getLevel().getGameTime() + partialTicks;
        } else {
            state.itemRenderState.clear();
        }
    }

    @Override
    public void submit(PedestalRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        if (!state.itemRenderState.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(0.5, 1.2, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(state.gameTime * 2.0F));
            poseStack.scale(0.65F, 0.65F, 0.65F);
            state.itemRenderState.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            poseStack.popPose();
        }
    }
}