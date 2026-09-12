package nicusha.gadget_lab.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import nicusha.gadget_lab.GadgetLab;
import nicusha.gadget_lab.items.materials.ArmourMaterials;
import nicusha.gadget_lab.registry.ItemRegistry;

import static nicusha.gadget_lab.GadgetLab.MODID;

@EventBusSubscriber(modid = GadgetLab.MODID)
public class GravityBoots extends ItemMod {

    public GravityBoots() {
        super(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MODID, "gravity_boots"))).humanoidArmor(ArmourMaterials.GRAVITY_BOOTS, ArmorType.BOOTS));
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.gravity_boots)) {
            player.fallDistance = 0;
            player.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, 3, 2, false, false, false));
        }
    }

}
