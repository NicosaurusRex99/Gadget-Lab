package nicusha.gadget_lab.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import nicusha.gadget_lab.GadgetLab;
import nicusha.gadget_lab.items.materials.ArmourMaterials;
import nicusha.gadget_lab.registry.ItemRegistry;

import java.util.List;

import static nicusha.gadget_lab.GadgetLab.MODID;

@EventBusSubscriber(modid = GadgetLab.MODID)
public class InvisibilityCloak extends ArmorItem {

    public InvisibilityCloak() {
        super(ArmourMaterials.INVISIBILITY_CLOAK, ArmorType.CHESTPLATE, new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MODID, "invisibility_cloak"))));
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.getInventory().armor.get(2).is(ItemRegistry.invisibility_cloak)) {
            player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 3, 0, false, false, false));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip." + stack.getItem().getDescriptionId()));
    }
}
