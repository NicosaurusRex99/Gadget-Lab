package nicusha.gadget_lab.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.registries.*;
import nicusha.gadget_lab.Main;
import nicusha.gadget_lab.client.RenderSmokeBomb;
import nicusha.gadget_lab.entities.EntitySmokeBomb;

import static nicusha.gadget_lab.Main.MODID;

public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<EntitySmokeBomb>> SMOKE_BOMB = registerProjectile(EntitySmokeBomb::new, "smoke_bomb");


    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
    event.registerEntityRenderer(SMOKE_BOMB.get(), (Context context) -> new RenderSmokeBomb<>(context, ResourceLocation.fromNamespaceAndPath(MODID,"textures/item/smoke_bomb.png")));
    }

    private static final <T extends Projectile> DeferredHolder<EntityType<?>, EntityType<T>> registerProjectile(EntityType.EntityFactory<T> factory, String entityName, float width, float length) {
        return ENTITIES.register(entityName, () -> EntityType.Builder.of(factory, MobCategory.MISC).sized(width, length).setTrackingRange(120).setUpdateInterval(20).build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Main.MODID, entityName))));
    }
    private static final <T extends Projectile> DeferredHolder<EntityType<?>, EntityType<T>> registerProjectile(EntityType.EntityFactory<T> factory, String entityName) {
        return registerProjectile(factory, entityName, 0.25F, 0.25F);
    }

}
