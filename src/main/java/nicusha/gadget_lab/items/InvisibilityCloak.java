package nicusha.gadget_lab.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.fml.common.EventBusSubscriber;
import nicusha.gadget_lab.GadgetLab;
import nicusha.gadget_lab.items.materials.ArmourMaterials;
import org.jspecify.annotations.Nullable;

import static nicusha.gadget_lab.GadgetLab.MODID;

@EventBusSubscriber(modid = GadgetLab.MODID)
public class InvisibilityCloak extends ItemMod {

    public InvisibilityCloak() {
        super(new Item.Properties().humanoidArmor(ArmourMaterials.INVISIBILITY_CLOAK, ArmorType.CHESTPLATE).stacksTo(1).setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MODID, "invisibility_cloak"))));
    }

    @Override
    public void inventoryTick(ItemStack itemStack, ServerLevel level, Entity owner, @Nullable EquipmentSlot slot) {
    Player player = (Player) owner;
    if(player.getItemBySlot(EquipmentSlot.CHEST).is(this)){
        player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 3, 0, false, false, false));
        }
    }
}
