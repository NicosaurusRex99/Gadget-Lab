package nicusha.gadget_lab.registry;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.*;
import nicusha.gadget_lab.items.*;

import java.util.function.Supplier;

import static nicusha.gadget_lab.Main.MODID;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final RegistryObject<Item> rebreather = registerItem("rebreather", () -> new Rebreather());
    public static final RegistryObject<Item> portable_crafting_table = registerItem("portable_crafting_table", () -> new PortableCraftingTable(new Item.Properties().stacksTo(1)));


    private static <T extends Item> RegistryObject<T> registerItem(String registryId, Supplier<T> item) {
        return ITEMS.register(registryId, item);
    }
}
