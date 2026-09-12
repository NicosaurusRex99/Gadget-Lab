package nicusha.gadget_lab.block_entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import nicusha.gadget_lab.registry.BlockRegistry;
import org.jetbrains.annotations.Nullable;

public class PedestalBlockEntity extends BlockEntity {
    private ItemStack inventory = ItemStack.EMPTY;
    private long lastChangeTime, lastInteractTime;
    private static final long INTERACT_COOLDOWN = 5;

    public PedestalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.PEDESTAL.get(), pos, state);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.store("Inventory", ItemStack.OPTIONAL_CODEC, this.inventory);
        output.putLong("LastChangeTime", this.lastChangeTime);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.inventory = input.read("Inventory", ItemStack.OPTIONAL_CODEC).orElse(ItemStack.EMPTY);
        this.lastChangeTime = input.getLongOr("LastChangeTime", 0L);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveCustomOnly(registries);
    }

    public void setItem(ItemStack itemStack) {
        this.inventory = itemStack;
        this.setChanged();
        if (this.level != null && !this.level.isClientSide()) {
            this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    public boolean isEmpty() {
        return this.inventory.isEmpty();
    }

    public ItemStack getItem() {
        return this.inventory;
    }

    public boolean hasItem() {
        return !this.inventory.isEmpty();
    }

    public void interact(Player player) {
        long currentTime = player.level().getGameTime();
        if (currentTime - lastInteractTime < INTERACT_COOLDOWN) {
            return;
        }
        lastInteractTime = currentTime;

        ItemStack heldItem = player.getMainHandItem();
        ItemStack pedestalItem = getItem();

        if (heldItem.isEmpty() && !pedestalItem.isEmpty()) {
            player.getInventory().add(pedestalItem.copy());
            setItem(ItemStack.EMPTY);
        } else if (!heldItem.isEmpty() && pedestalItem.isEmpty()) {
            if (heldItem.getCount() == 1) {
                setItem(heldItem.copy());
                heldItem.consume(1, player);
            }
        }
    }

    public void dropItem(Level world, BlockPos pos) {
        ItemStack itemStack = getItem();
        if (!world.isClientSide() && !itemStack.isEmpty()) {
            ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5, itemStack.copy());
            world.addFreshEntity(itemEntity);
            setItem(ItemStack.EMPTY);
        }
    }
}