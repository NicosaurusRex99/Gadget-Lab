package nicusha.gadget_lab.client;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import nicusha.gadget_lab.Main;
import nicusha.gadget_lab.registry.ItemRegistry;


public class RenderSmokeBomb<T extends ThrowableProjectile> extends EntityRenderer<T> {
    protected final ResourceLocation TEXTURE;
    protected final RenderType renderType;
    private final ItemRenderer itemRenderer;

    public RenderSmokeBomb(final Context context, final String name) {
        this(context, ResourceLocation.fromNamespaceAndPath(Main.MODID, "textures/projectiles/" + name + ".png"));
    }
    public RenderSmokeBomb(final Context context) {
        this(context, ResourceLocation.withDefaultNamespace("textures/particle/generic_0.png"));
    }
    public RenderSmokeBomb(final Context context, final ResourceLocation texture) {
        super(context);
        this.TEXTURE = texture;
        this.renderType = RenderType.entityCutoutNoCull(texture);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (pEntity.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(pEntity) < 12.25)) {
            pPoseStack.pushPose();
            pPoseStack.scale(0.5F, 0.5F, 0.5F);
            pPoseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
            pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            this.itemRenderer.renderStatic(
                            new ItemStack(ItemRegistry.smoke_bomb.asItem()),
                            ItemDisplayContext.GROUND,
                            pPackedLight,
                            OverlayTexture.NO_OVERLAY,
                            pPoseStack,
                            pBuffer,
                            pEntity.level(),
                            pEntity.getId()
                    );
            pPoseStack.popPose();
            super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return TEXTURE;
    }
}