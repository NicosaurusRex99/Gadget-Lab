package nicusha.gadget_lab.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.*;
import nicusha.gadget_lab.Main;
import nicusha.gadget_lab.client.RenderSmokeBomb;
import nicusha.gadget_lab.entities.EntitySmokeBomb;

import static nicusha.gadget_lab.Main.MODID;

public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<EntitySmokeBomb>> SMOKE_BOMB = registerProjectile(EntitySmokeBomb::new, "smoke_bomb");

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(SMOKE_BOMB.get(), (context) -> new RenderSmokeBomb<>(context, ItemRegistry.smoke_bomb::value));
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {

    }


    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {

    }

    private static <T extends Projectile> DeferredHolder<EntityType<?>, EntityType<T>> registerProjectile(EntityType.EntityFactory<T> factory, String entityName, float width, float length) {
        return ENTITIES.register(entityName, () -> EntityType.Builder.of(factory, MobCategory.MISC).sized(width, length).setTrackingRange(120).setUpdateInterval(20).build(ResourceLocation.fromNamespaceAndPath(MODID, entityName).getPath()));
    }
    private static <T extends Projectile> DeferredHolder<EntityType<?>, EntityType<T>> registerProjectile(EntityType.EntityFactory<T> factory, String entityName) {
        return registerProjectile(factory, entityName, .25F, .25F);
    }
}
