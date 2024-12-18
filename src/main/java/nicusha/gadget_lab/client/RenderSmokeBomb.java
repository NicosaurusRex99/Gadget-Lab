package nicusha.gadget_lab.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import nicusha.gadget_lab.GadgetLab;
import nicusha.gadget_lab.client.state.SmokeBombRenderState;
import nicusha.gadget_lab.registry.ItemRegistry;


@OnlyIn(Dist.CLIENT)
public class RenderSmokeBomb<T extends ThrowableProjectile> extends EntityRenderer<T, SmokeBombRenderState> {
    protected final ResourceLocation TEXTURE;
    protected final RenderType renderType;
    private final ItemModelResolver itemModelResolver;

    public RenderSmokeBomb(final Context context, final String name) {
        this(context, ResourceLocation.fromNamespaceAndPath(GadgetLab.MODID, "textures/projectiles/" + name + ".png"));
    }
    public RenderSmokeBomb(final Context context) {
        this(context, ResourceLocation.withDefaultNamespace("textures/particle/generic_0.png"));
    }

    @Override
    public SmokeBombRenderState createRenderState() {
        return new SmokeBombRenderState();
    }

    public RenderSmokeBomb(final Context context, final ResourceLocation texture) {
        super(context);
        this.TEXTURE = texture;
        this.renderType = RenderType.entityCutoutNoCull(texture);
        this.itemModelResolver = context.getItemModelResolver();
    }

    public void render(SmokeBombRenderState renderState, PoseStack pose, MultiBufferSource source, int packedLight) {
        pose.pushPose();
        pose.scale(0.5F, 0.5F, 0.5F);
        pose.mulPose(this.entityRenderDispatcher.cameraOrientation());
        renderState.item.render(pose, source, packedLight, OverlayTexture.NO_OVERLAY);
        pose.popPose();
        super.render(renderState, pose, source, packedLight);
    }

    public void extractRenderState(T entity, SmokeBombRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        this.itemModelResolver.updateForNonLiving(state.item, ItemRegistry.smoke_bomb.toStack(), ItemDisplayContext.GROUND, entity);
    }
//    @Override
//    public void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
//        if (pEntity.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(pEntity) < 12.25)) {
//            pPoseStack.pushPose();
//            pPoseStack.scale(0.5F, 0.5F, 0.5F);
//            pPoseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
//            pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
//            this.itemRenderer.renderStatic(
//                            new ItemStack(ItemRegistry.smoke_bomb.asItem()),
//                            ItemDisplayContext.GROUND,
//                            pPackedLight,
//                            OverlayTexture.NO_OVERLAY,
//                            pPoseStack,
//                            pBuffer,
//                            pEntity.level(),
//                            pEntity.getId()
//                    );
//            pPoseStack.popPose();
//            super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
//        }
//    }
//
//    @Override
//    public ResourceLocation getTextureLocation(T entity) {
//        return TEXTURE;
//    }
}