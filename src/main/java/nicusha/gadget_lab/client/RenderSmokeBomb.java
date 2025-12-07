package nicusha.gadget_lab.client;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import nicusha.gadget_lab.Main;
import nicusha.gadget_lab.registry.ItemRegistry;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class RenderSmokeBomb<T extends Projectile> extends EntityRenderer<T> {
    private final Supplier<Item> item;
    private final ItemRenderer itemRenderer;
    public RenderSmokeBomb(EntityRendererProvider.Context context, Supplier<Item> item) {
        super(context);
        itemRenderer = context.getItemRenderer();
        this.item = item;
    }
    @Override public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if(entity.tickCount > 2 || !(entityRenderDispatcher.camera.getEntity().distanceToSqr(entity) < 12.25)) {
            poseStack.pushPose();
            poseStack.mulPose(entityRenderDispatcher.cameraOrientation());
            itemRenderer.renderStatic(item.get().getDefaultInstance(), ItemDisplayContext.GROUND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, entity.level(), entity.getId());
            poseStack.popPose();
            super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        }
    }
    @SuppressWarnings("deprecation")
    @Override public ResourceLocation getTextureLocation(Projectile entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}