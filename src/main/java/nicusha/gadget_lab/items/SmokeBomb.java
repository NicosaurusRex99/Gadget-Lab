package nicusha.gadget_lab.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import nicusha.gadget_lab.entities.EntitySmokeBomb;
import nicusha.gadget_lab.registry.EntityRegistry;

import static nicusha.gadget_lab.GadgetLab.MODID;

public class SmokeBomb extends ItemMod {
    public SmokeBomb() {
        super(new Item.Properties().stacksTo(16).setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MODID, "smoke_bomb"))));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        player.swing(hand, true);
        if (!level.isClientSide()) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SMOKER_SMOKE, SoundSource.PLAYERS, 0.8F, 0.8F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            EntitySmokeBomb bomb = new EntitySmokeBomb(EntityRegistry.SMOKE_BOMB.get(), level, player);
            bomb.moveOrInterpolateTo(player.position(), player.getXRot(), player.getYRot());
            bomb.setItem(itemStack.copy());
            bomb.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(bomb);
            if (!player.hasInfiniteMaterials()) {
                itemStack.shrink(1);
            }
        }
        return InteractionResult.SUCCESS;
    }
}