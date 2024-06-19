package nicusha.gadget_lab.items.materials;

import net.minecraft.Util;
import net.minecraft.core.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import nicusha.gadget_lab.Main;

import java.util.*;
import java.util.function.Supplier;

public class ArmourMaterials {

    public static final Holder<ArmorMaterial> GRAVITY_BOOTS = register("gravity_boots", Util.make(new EnumMap<>(ArmorItem.Type.class), armourType -> {
        armourType.put(ArmorItem.Type.BOOTS, 1);
        armourType.put(ArmorItem.Type.LEGGINGS, 0);
        armourType.put(ArmorItem.Type.CHESTPLATE, 0);
        armourType.put(ArmorItem.Type.HELMET, 0);
        armourType.put(ArmorItem.Type.BODY, 0);
    }), 0, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, () -> Ingredient.of(Items.SLIME_BALL));
    public static final Holder<ArmorMaterial> REBREATHER = register("rebreather", Util.make(new EnumMap<>(ArmorItem.Type.class), armourType -> {
        armourType.put(ArmorItem.Type.BOOTS, 0);
        armourType.put(ArmorItem.Type.LEGGINGS, 0);
        armourType.put(ArmorItem.Type.CHESTPLATE, 0);
        armourType.put(ArmorItem.Type.HELMET, 0);
        armourType.put(ArmorItem.Type.BODY, 0);
    }), 0, SoundEvents.ARMOR_EQUIP_CHAIN, 0.0F, 0.0F, () -> Ingredient.of(Items.AIR));

    private static Holder<ArmorMaterial> register(String name, EnumMap<ArmorItem.Type, Integer> armourType, int enchantability, Holder<SoundEvent> equipSound,
            float toughness, float knockbackResistance, Supplier<Ingredient> repair) {
        List<ArmorMaterial.Layer> list = List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(Main.MODID, name)));
        return register(name, armourType, enchantability, equipSound, toughness, knockbackResistance, repair, list);
    }

    private static Holder<ArmorMaterial> register(String name, EnumMap<ArmorItem.Type, Integer> armourType, int enchantability, Holder<SoundEvent> equipSound,
        float toughness, float knockbackResistance, Supplier<Ingredient> repair, List<ArmorMaterial.Layer> pLayers) {
        EnumMap<ArmorItem.Type, Integer> enummap = new EnumMap<>(ArmorItem.Type.class);
        for (ArmorItem.Type armoritem$type : ArmorItem.Type.values()) {
            enummap.put(armoritem$type, armourType.get(armoritem$type));
        }
        return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, ResourceLocation.fromNamespaceAndPath(Main.MODID, name), new ArmorMaterial(enummap, enchantability, equipSound, repair, pLayers, toughness, knockbackResistance)
        );
    }
}
