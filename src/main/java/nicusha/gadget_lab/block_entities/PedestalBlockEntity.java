package nicusha.gadget_lab.block_entities;

import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import nicusha.gadget_lab.registry.BlockRegistry;

public class PedestalBlockEntity extends BlockEntity {
    private final ItemStackHandler inventory = new ItemStackHandler(1);
    private long lastChangeTime, lastInteractTime;
    private static final long INTERACT_COOLDOWN = 5;

    public PedestalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.PEDESTAL.get(), pos, state);
    }

    @Override
    public void saveAdditional(CompoundTag data, HolderLookup.Provider registries) {
        super.saveAdditional(data, registries);
        var inv = inventory;
        data.put("Inventory", inv.serializeNBT(registries));
        data.putLong("LastChangeTime", lastChangeTime);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        var inv = inventory;
        inv.deserializeNBT(pRegistries, pTag.getCompound("Inventory"));
        lastChangeTime = pTag.getLong("LastChangeTime");
    }

    public boolean isEmpty() {
        return inventory.getStackInSlot(0).isEmpty();
    }

    public ItemStack getItem() {
        return inventory.getStackInSlot(0);
    }

    public void setItem(ItemStack itemStack) {
        inventory.setStackInSlot(0, itemStack);
        this.setChanged();
    }

    public boolean hasItem() {
        return !getItem().isEmpty();
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
            player.addItem(pedestalItem);
            setItem(ItemStack.EMPTY);
        } else if (!heldItem.isEmpty() && pedestalItem.isEmpty()) {
            if (heldItem.getCount() == 1) {
                setItem(heldItem.copy());
                heldItem.setCount(0);
            }
        }
    }


    public void dropItem(Level world, BlockPos pos) {
        ItemStack itemStack = getItem();
        if (!world.isClientSide && !itemStack.isEmpty()) {
            ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5, itemStack.copy());
            world.addFreshEntity(itemEntity);
            setItem(ItemStack.EMPTY);
        }
    }
}
