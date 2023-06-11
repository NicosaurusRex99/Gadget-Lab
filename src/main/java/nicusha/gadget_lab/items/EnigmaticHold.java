package nicusha.gadget_lab.items;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.*;
import net.minecraftforge.registries.ForgeRegistries;


public class EnigmaticHold extends ItemMod {
    private static final String CAPTURED_MOBS_TAG_KEY = "capturedMobs";

    public EnigmaticHold() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        if (!player.level().isClientSide && entity.getType() != null) {
            CompoundTag entityTag = new CompoundTag();
            entity.saveWithoutId(entityTag);
            entityTag.putString("id", ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString());

            ListTag capturedMobsTag = stack.getOrCreateTag().getList(CAPTURED_MOBS_TAG_KEY, 10);
            capturedMobsTag.add(entityTag);
            stack.getOrCreateTag().put(CAPTURED_MOBS_TAG_KEY, capturedMobsTag);

            player.setItemInHand(hand, stack);
            entity.remove(Entity.RemovalReason.DISCARDED);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        InteractionHand hand = context.getHand();
        Level world = context.getLevel();
        ItemStack itemStack = player.getItemInHand(hand);

        if (!world.isClientSide()) {
            BlockPos blockPos = context.getClickedPos();
            ListTag capturedMobsTag = itemStack.getOrCreateTag().getList(CAPTURED_MOBS_TAG_KEY, 10);
            if (capturedMobsTag.isEmpty()) {
                return InteractionResult.PASS;
            }

            CompoundTag tag = (CompoundTag) capturedMobsTag.remove(capturedMobsTag.size() - 1);
            EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(tag.getString("id")));
            if (type != null) {
                type.spawn((ServerLevel) world, tag, null, blockPos.above(), MobSpawnType.SPAWN_EGG, false, false);
                player.setItemInHand(hand, itemStack);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

}