package nicusha.gadget_lab.items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.phys.Vec3;
import nicusha.gadget_lab.component.CapturedMobsComponent;
import nicusha.gadget_lab.registry.DataComponentRegistry;

import java.util.List;
import java.util.function.Consumer;

import static nicusha.gadget_lab.GadgetLab.MODID;

public class EnigmaticHold extends ItemMod {
    public EnigmaticHold() {
        super(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MODID, "enigmatic_hold"))));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        if (entity instanceof Player || !entity.isAlive()) {
            return InteractionResult.PASS;
        }
        Level world = player.level();
        if (!world.isClientSide()) {
            CapturedMobsComponent currentComponent = stack.getOrDefault(DataComponentRegistry.CAPTURED_MOBS.get(), CapturedMobsComponent.EMPTY);
            if (currentComponent.isFull()) {
                player.sendSystemMessage(Component.translatable("Enigmatic Hold is full! (10/10)").withStyle(ChatFormatting.RED));
                return InteractionResult.FAIL;
            }
            ProblemReporter.Collector collector = new ProblemReporter.Collector();
            TagValueOutput output = TagValueOutput.createWithContext(collector, world.registryAccess());
            entity.saveWithoutId(output);
            CompoundTag entityData = output.buildResult();
            String entityId = EntityType.getKey(entity.getType()).toString();
            entityData.putString("id", entityId);
            entityData.remove("UUID");
            Component displayName = entity.getDisplayName();
            CapturedMobsComponent.StoredMob storedMob = new CapturedMobsComponent.StoredMob(entityData, displayName);
            stack.set(DataComponentRegistry.CAPTURED_MOBS.get(), currentComponent.withAddedMob(storedMob));
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.2F);
            entity.discard();
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();
        if (player == null) {
            return InteractionResult.PASS;
        }
        CapturedMobsComponent currentComponent = stack.getOrDefault(DataComponentRegistry.CAPTURED_MOBS.get(), CapturedMobsComponent.EMPTY);
        if (currentComponent.isEmpty()) {
            return InteractionResult.PASS;
        }
        if (!world.isClientSide() && world instanceof ServerLevel serverLevel) {
            CapturedMobsComponent.StoredMob[] poppedMob = new CapturedMobsComponent.StoredMob[1];
            CapturedMobsComponent updatedComponent = currentComponent.withRemovedFirst(poppedMob);
            if (poppedMob[0] != null) {
                BlockPos targetPos = context.getClickedPos();
                Direction face = context.getClickedFace();
                BlockPos spawnPos = targetPos.relative(face);
                CompoundTag tag = poppedMob[0].entityData();
                Entity entity = EntityType.loadEntityRecursive(tag, serverLevel, new EntitySpawnRequest(EntitySpawnReason.SPAWN_ITEM_USE, false),
                        (EntityProcessor) spawnedEntity -> {
                            spawnedEntity.moveOrInterpolateTo(new Vec3(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5), player.getYRot(), 0.0F);
                            return spawnedEntity;
                        }
                );
                if (entity != null) {
                    serverLevel.addFreshEntity(entity);
                    stack.set(DataComponentRegistry.CAPTURED_MOBS.get(), updatedComponent);
                    world.playSound(null, spawnPos, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 0.8F);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
        CapturedMobsComponent component = itemStack.getOrDefault(DataComponentRegistry.CAPTURED_MOBS.get(), CapturedMobsComponent.EMPTY);
        List<CapturedMobsComponent.StoredMob> mobs = component.mobs();
        builder.accept(Component.literal("Stored Mobs (" + mobs.size() + "/10):").withStyle(ChatFormatting.GOLD));
        if (mobs.isEmpty()) {
            builder.accept(Component.literal("  Empty").withStyle(ChatFormatting.GRAY));
        } else {
            for (int i = 0; i < mobs.size(); i++) {
                Component mobName = mobs.get(i).displayName();
                builder.accept(Component.literal(" " + (i + 1) + ". ").withStyle(ChatFormatting.DARK_GRAY).append(mobName.copy().withStyle(ChatFormatting.YELLOW)));
            }
        }
    }
}