package nicusha.gadget_lab.enums;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;
import nicusha.gadget_lab.Main;

import javax.annotation.Nullable;

import static net.minecraft.sounds.SoundEvents.*;

public enum ArmorTypes implements ArmorMaterial {
    REBREATHER(	0, 0, 0,0, 0, 6000, 0, ARMOR_EQUIP_CHAIN);

    final String textureLocation, name;
    final SoundEvent equipSound;
    final Ingredient repairIngredient;
    final int headArmor, chestArmor, legsArmor, feetArmor, durability, enchantability;
    final float toughness;
    ArmorTypes(int headArmor, int chestArmor, int legsArmor, int feetArmor, float toughness, int durability, int enchantability) {
        this(null, null, headArmor, chestArmor, legsArmor, feetArmor, toughness, durability, enchantability, null, null);
    }
    ArmorTypes(int headArmor, int chestArmor, int legsArmor, int feetArmor, float toughness, int durability, int enchantability, @Nullable SoundEvent equipSound) {
        this(null, null, headArmor, chestArmor, legsArmor, feetArmor, toughness, durability, enchantability, equipSound, null);
    }
    ArmorTypes(int headArmor, int chestArmor, int legsArmor, int feetArmor, float toughness, int durability, int enchantability, @Nullable String repairIngredient) {
        this(null, null, headArmor, chestArmor, legsArmor, feetArmor, toughness, durability, enchantability, null, repairIngredient);
    }
    ArmorTypes(int headArmor, int chestArmor, int legsArmor, int feetArmor, float toughness, int durability, int enchantability, @Nullable SoundEvent equipSound, @Nullable String repairIngredient) {
        this(null, null, headArmor, chestArmor, legsArmor, feetArmor, toughness, durability, enchantability, equipSound, repairIngredient);
    }
    ArmorTypes(@Nullable String customTextureLocation, @Nullable String name, int headArmor, int chestArmor, int legsArmor, int feetArmor, float toughness, int durability, int enchantability, @Nullable SoundEvent equipSound, @Nullable String repairIngredient) {
        String nonnullName = name == null ? toString().toLowerCase() : name;
        this.textureLocation = customTextureLocation == null ? "gadget_lab_" + nonnullName + "armor" : customTextureLocation;
        this.name = Main.MODID + ":" + nonnullName;
        this.headArmor = headArmor;
        this.chestArmor = chestArmor;
        this.legsArmor = legsArmor;
        this.feetArmor = feetArmor;
        this.toughness = toughness;
        this.durability = durability;
        this.enchantability = enchantability;
        this.equipSound = equipSound == null ? ARMOR_EQUIP_GENERIC : equipSound;
        this.repairIngredient = repairIngredient == null ? Ingredient.EMPTY : Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Main.MODID, repairIngredient)));

    }
    public String getTextureLocation() {return textureLocation;}


    @Override public int getDurabilityForType(ArmorItem.Type type) {return durability;}
    @Override public int getEnchantmentValue() {return enchantability;}
    @Override public SoundEvent getEquipSound() {return equipSound;}
    @Override public Ingredient getRepairIngredient() {return repairIngredient;}
    @Override public String getName() {return name;}
    @Override public float getToughness() {return toughness;}
    @Override public float getKnockbackResistance() {return 0;}
    @Override public int getDefenseForType(ArmorItem.Type type) {
        return switch(type) {
            case HELMET -> headArmor;
            case CHESTPLATE -> chestArmor;
            case LEGGINGS -> legsArmor;
            default -> feetArmor;
        };
    }
}