package nicusha.gadget_lab.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;

import java.util.*;

import static nicusha.gadget_lab.Main.MODID;

public class EnigmaticHold extends ItemMod {
    public EnigmaticHold() {
        super(new Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MODID, "enigmatic_hold"))));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        //TODO - fix enigmatic hold
//        if (!player.level().isClientSide && entity.getType() != null && stack.get(DataComponentRegistry.SELECTED_MOB.get()) == null) {
//            stack.set(DataComponentRegistry.SELECTED_MOB.get(), new MobFromUUIDComponent(Optional.of(entity.getUUID()), Optional.of(entity.getDisplayName())));
//        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
//        Player player = context.getPlayer();
//        InteractionHand hand = context.getHand();
//        Level world = context.getLevel();
//        ItemStack stack = player.getItemInHand(hand);
//
//        if (!world.isClientSide()) {
//            BlockPos pos = context.getClickedPos();
//            MobFromUUIDComponent component = stack.get(DataComponentRegistry.SELECTED_MOB.get());
//            if(component != null && component.uuid() != null) {
//                if (world instanceof ServerLevel level) {
//                    Entity entity = level.getEntities().get(component.uuid().get());
//                    if (entity != null) {
//                        entity.spawnAtLocation(stack);
//                        entity.moveTo(pos.getX(), pos.getY() + 1, pos.getZ());
//                        world.addFreshEntity(entity);
//                    }
//                }
//            }
//            stack.remove(DataComponentRegistry.SELECTED_MOB.get());
//            }
        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.wip."+stack.getItem().getDescriptionId()));
    }

}