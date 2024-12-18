package nicusha.gadget_lab.items.materials;

import net.minecraft.core.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.*;

import static nicusha.gadget_lab.GadgetLab.MODID;

public interface ArmourMaterials {

    ArmorMaterial GRAVITY_BOOTS = registerArmorMaterial(
            "gravity_boots", 0,
            createDefenseMap(1, 0, 0, 0, 0),
            1, SoundEvents.ARMOR_EQUIP_IRON,
            0.0F, 0.0F,
            ItemTags.create(ResourceLocation.fromNamespaceAndPath(MODID, "slime_ball_repair"))
    );

    ArmorMaterial REBREATHER = registerArmorMaterial(
            "rebreather", 0,
            createDefenseMap(0, 0, 0, 0, 0),
            1, SoundEvents.ARMOR_EQUIP_CHAIN,
            0.0F, 0.0F,
            ItemTags.create(ResourceLocation.fromNamespaceAndPath(MODID, "air_repair"))
    );

    ArmorMaterial INVISIBILITY_CLOAK = registerArmorMaterial(
            "invisibility_cloak", 0,
            createDefenseMap(0, 0, 0, 0, 0),
            1, SoundEvents.ARMOR_EQUIP_GENERIC,
            0.0F, 0.0F,
            ItemTags.create(ResourceLocation.fromNamespaceAndPath(MODID, "air_repair"))
    );

    static ResourceKey<EquipmentAsset> createId(String name) {
        return ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(MODID, name));
    }

    static EnumMap<ArmorType, Integer> createDefenseMap(int boots, int leggings, int chestplate, int helmet, int body) {
        EnumMap<ArmorType, Integer> defenseMap = new EnumMap<>(ArmorType.class);
        defenseMap.put(ArmorType.BOOTS, boots);
        defenseMap.put(ArmorType.LEGGINGS, leggings);
        defenseMap.put(ArmorType.CHESTPLATE, chestplate);
        defenseMap.put(ArmorType.HELMET, helmet);
        defenseMap.put(ArmorType.BODY, body);
        return defenseMap;
    }

    private static ArmorMaterial registerArmorMaterial(
            String name,
            int durability,
            EnumMap<ArmorType, Integer> defenseMap,
            int enchantmentValue,
            Holder<SoundEvent> equipSound,
            float toughness,
            float knockbackResistance,
            TagKey<Item> repairIngredient
    ) {
        return createArmorMaterial(
                durability,
                defenseMap,
                enchantmentValue,
                equipSound,
                toughness,
                knockbackResistance,
                repairIngredient,
                createId(name)
        );
    }

    private static ArmorMaterial createArmorMaterial(
            int durability,
            EnumMap<ArmorType, Integer> defenseMap,
            int enchantmentValue,
            Holder<SoundEvent> equipSound,
            float toughness,
            float knockbackResistance,
            TagKey<Item> repairIngredient,
            ResourceKey<EquipmentAsset> assetKey
    ) {
        EnumMap<ArmorType, Integer> mappedDefense = new EnumMap<>(ArmorType.class);

        for (ArmorType type : ArmorType.values()) {
            mappedDefense.put(type, defenseMap.get(type));
        }

        return new ArmorMaterial(
                durability,
                mappedDefense,
                enchantmentValue,
                equipSound,
                toughness,
                knockbackResistance,
                repairIngredient,
                assetKey
        );
    }
}
