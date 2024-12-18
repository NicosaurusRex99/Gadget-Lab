package nicusha.gadget_lab.items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import nicusha.gadget_lab.entities.EntitySmokeBomb;
import nicusha.gadget_lab.registry.EntityRegistry;

import java.util.List;

import static nicusha.gadget_lab.GadgetLab.MODID;

public class SmokeBomb extends ItemMod {
    public SmokeBomb() {
        super(new Item.Properties().stacksTo(16).setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MODID, "smoke_bomb"))));
    }

    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            EntitySmokeBomb bomb = new EntitySmokeBomb(EntityRegistry.SMOKE_BOMB.get(), level, player);
            bomb.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(bomb);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SMOKER_SMOKE, SoundSource.PLAYERS, 1.0f, 1.0f);
            if (!player.isCreative())
                player.getItemInHand(hand).shrink(1);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.wip." + MODID).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.RED));
    }
}
