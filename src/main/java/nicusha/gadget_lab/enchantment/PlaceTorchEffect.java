package nicusha.gadget_lab.enchantment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentLocationBasedEffect;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public record PlaceTorchEffect(int maxBlockLight, int maxCombinedLight, int durabilityCost) implements EnchantmentLocationBasedEffect {

    public static final MapCodec<PlaceTorchEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.intRange(0, 15).optionalFieldOf("max_block_light", 0).forGetter(PlaceTorchEffect::maxBlockLight), Codec.intRange(0, 15).optionalFieldOf("max_combined_light", 7).forGetter(PlaceTorchEffect::maxCombinedLight), Codec.intRange(0, 100).optionalFieldOf("durability_cost", 1).forGetter(PlaceTorchEffect::durabilityCost)).apply(instance, PlaceTorchEffect::new));
    public static final PlaceTorchEffect DEFAULT = new PlaceTorchEffect(0, 7, 1);

    @Override
    public void onChangedBlock(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 pos, boolean newlyApplied) {
        if (!(entity instanceof Player player) || player.isSpectator()) return;
        BlockPos feet = entity.blockPosition();
        BlockState torch = Blocks.TORCH.defaultBlockState();
        BlockPos targetPos = isValidTarget(level, feet, torch) ? feet : (isValidTarget(level, feet.above(), torch) ? feet.above() : null);
        if (targetPos == null || level.getBrightness(LightLayer.BLOCK, targetPos) > maxBlockLight || level.getMaxLocalRawBrightness(targetPos) > maxCombinedLight) {
            return;
        }
        ItemStack stack = item.itemStack();
        boolean charge = durabilityCost > 0 && !player.isCreative() && stack.isDamageableItem();
        if (charge && stack.getMaxDamage() - stack.getDamageValue() < durabilityCost + 1) {
            return;
        }
        BlockState current = level.getBlockState(targetPos);
        if (!current.isAir() && current.is(BlockTags.REPLACEABLE)) {
            level.destroyBlock(targetPos, true, player);
        }
        level.setBlock(targetPos, torch, Block.UPDATE_ALL);
        level.playSound(null, targetPos, torch.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
        if (charge) {
            stack.setDamageValue(stack.getDamageValue() + durabilityCost);
        }
    }

    private boolean isValidTarget(ServerLevel level, BlockPos pos, BlockState torch) {
        BlockState state = level.getBlockState(pos);
        return (state.isAir() || state.is(BlockTags.REPLACEABLE)) && torch.canSurvive(level, pos);
    }

    @Override
    public MapCodec<? extends EnchantmentLocationBasedEffect> codec() {
        return CODEC;
    }
}