package nicusha.gadget_lab.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.neoforged.api.distmarker.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.registries.*;
import nicusha.gadget_lab.Main;
import nicusha.gadget_lab.client.RenderProjectile;
import nicusha.gadget_lab.entities.EntitySmokeBomb;

import static nicusha.gadget_lab.Main.MODID;

public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<EntitySmokeBomb>> SMOKE_BOMB = registerProjectile(EntitySmokeBomb::new, "smoke_bomb");



    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        Main.LOGGER.info("[Gadget Lab] Registered entity renders");
        //Projectile
        event.registerEntityRenderer(SMOKE_BOMB.get(), (Context context) -> new RenderProjectile<>(context, new ResourceLocation(MODID,"textures/item/smoke_bomb.png")));

    }

    private static final <T extends Projectile> DeferredHolder<EntityType<?>, EntityType<T>> registerProjectile(EntityType.EntityFactory<T> factory, String entityName, float width, float length) {
//        return ENTITIES.register(entityName, () -> EntityType.Builder.of(factory, MobCategory.MISC).sized(width, length).setTrackingRange(120).setUpdateInterval(20).build(new ResourceLocation(Main.MODID, entityName).getPath()));
    return null;
    }
    private static final <T extends Projectile> DeferredHolder<EntityType<?>, EntityType<T>> registerProjectile(EntityType.EntityFactory<T> factory, String entityName) {
        return registerProjectile(factory, entityName, 0.25F, 0.25F);
    }

}
