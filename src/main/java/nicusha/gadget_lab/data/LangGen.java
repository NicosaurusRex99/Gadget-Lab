package nicusha.gadget_lab.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import nicusha.gadget_lab.Main;
import nicusha.gadget_lab.registry.BlockRegistry;
import nicusha.gadget_lab.registry.EntityRegistry;
import nicusha.gadget_lab.registry.ItemRegistry;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class LangGen implements DataProvider {
    private final PackOutput packOutput;
    private final Map<String, String> translations = new HashMap<>();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public LangGen(PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        addTranslations();

        getExpectedKeys().forEach(key -> {
            if (!translations.containsKey(key)) {
                throw new IllegalStateException("No translation for " + key);
            }
        });

        JsonObject langJson = new JsonObject();
        translations.forEach(langJson::addProperty);
        Path langPath = getTranslationPath("en_us");
        return DataProvider.saveStable(cachedOutput, langJson, langPath);
    }

    private void addTranslations() {
        BlockRegistry.BLOCKS.getEntries().forEach(regObj -> {
            ResourceLocation blockId = regObj.getId();
            translations.put(
                    "block." + Main.MODID + "." + blockId.getPath(),
                    generateLocalizedNameFromPath(blockId.getPath())
            );
        });

        ItemRegistry.ITEMS.getEntries().forEach(regObj -> {
            ResourceLocation itemId = regObj.getId();
            translations.put(
                    "item." + Main.MODID + "." + itemId.getPath(),
                    generateLocalizedNameFromPath(itemId.getPath())
            );
        });

        EntityRegistry.ENTITIES.getEntries().forEach(regObj -> {
            ResourceLocation entityId = regObj.getId();
            translations.put(
                    "entity." + Main.MODID + "." + entityId.getPath(),
                    generateLocalizedNameFromPath(entityId.getPath())
            );
        });

        //Creative Tab
        translations.put("itemGroup.gadget_lab", "Gadget Lab");

        // Item Tooltips
        translations.put("tooltip.item.gadget_lab.rebreather", "Allows you to swim faster and breath underwater for 5 minutes");
        translations.put("tooltip.item.gadget_lab.portable_crafting_table", "A portable crafting table");
        translations.put("tooltip.item.gadget_lab.enigmatic_hold", "Holds up-to 10 mobs and places them in the same order they were captured in");
        translations.put("tooltip.wip.gadget_lab", "Currently does not function");
        translations.put("tooltip.item.gadget_lab.gravity_boots", "Jump higher and removes fall damage");
        translations.put("tooltip.item.gadget_lab.lucky_amulet", "Gives luck when in the players inventory");
        translations.put("tooltip.item.gadget_lab.fortune_amulet", "Gives fortune when in the players inventory");
        translations.put("tooltip.item.gadget_lab.harvesting_gloves", "Enhances crop yield");
        translations.put("tooltip.item.gadget_lab.teleportation_wand", "Teleports 32 blocks in the direction the player is looking, if there is a block between the player and the look spot it teleports just infront or on top");
        translations.put("tooltip.item.gadget_lab.pocket_watch", "Adds a hud with the game time");
        translations.put("tooltip.item.gadget_lab.climbing_gloves", "Allows you to climb 3 blocks high");
        translations.put("tooltip.item.gadget_lab.smoke_bombs", "Spawns a large area of smoke particles");
        translations.put("tooltip.item.gadget_lab.herbicide_spray", "Clears out all flora ontop of grass");
        translations.put("tooltip.item.gadget_lab.blaze_blaster", "Shoots blaze powder and creates fires");
        translations.put("tooltip.item.gadget_lab.smoke_bomb", "When thrown creates a smokescreen");
        translations.put("tooltip.item.gadget_lab.magnetic_glove", "Draws items towards the player");
        translations.put("tooltip.item.gadget_lab.invisibility_cloak", "Gives the player invisibility when worn");
        translations.put("tooltip.item.gadget_lab.launch_pad", "Launches the player fairly high");
        translations.put("tooltip.item.gadget_lab.energy_transmitter", "Wirelessly power machines in a small radius at the cost of power");
        translations.put("tooltip.item.gadget_lab.growth_accelerator", "Speeds up crop growth in a small radius");
        translations.put("tooltip.item.gadget_lab.void_chest", "Voids all items placed inside");
        translations.put("tooltip.item.gadget_lab.block_breaker", "Breaks blocks on the breaking face of the block breaker");
        translations.put("tooltip.item.gadget_lab.pedestal", "Holds items");
        translations.put("tooltip.item.gadget_lab.entity_magnet", "Attracts mobs, toggled via redstone.");
        translations.put("tooltip.item.gadget_lab.cloud_platform", "You can place this block mid air allowing you to create platforms while flying");
        translations.put("tooltip.item.gadget_lab.ore_extractor", "Mines ores directly below. Outputs above");
        translations.put("tooltip.item.gadget_lab.carbon_compressor", "Turns various carbon items into diamonds");
        translations.put("tooltip.item.gadget_lab.displacement_pad", "Teleports you to a predefined location");
        translations.put("tooltip.item.gadget_lab.holographic_projector", "Renders an entity above the block. Set via enigmatic hold");
        translations.put("tooltip.item.gadget_lab.soul_lantern", "Emits light and soul particles");
        translations.put("tooltip.item.gadget_lab.slime_siphon", "Collects slimeballs from slimes above");
        translations.put("tooltip.item.gadget_lab.quicksand", "Sand that can trap you");
        translations.put("tooltip.item.gadget_lab.fishermans_net", "Gathers regular fishing items");
        translations.put("tooltip.item.gadget_lab.sun_dial", "Tells the time");

        // Enchantments
        translations.put("enchantment.gadget_lab.magma_walker", "Magma Walker");
        translations.put("enchantment.gadget_lab.magma_walker.desc", "The Magma Walker enchantment temporarily freezes the lava around you and turns it into obsidian");

    }

    private Path getTranslationPath(String language) {
        return packOutput.getOutputFolder(PackOutput.Target.RESOURCE_PACK)
                .resolve(Main.MODID)
                .resolve("lang")
                .resolve(language + ".json");
    }

    private String generateLocalizedNameFromPath(String path) {
        String[] words = path.split("_");
        StringBuilder localizedName = new StringBuilder();
        for (String word : words) {
            localizedName.append(word.substring(0, 1).toUpperCase())
                    .append(word.substring(1).toLowerCase())
                    .append(" ");
        }
        return localizedName.toString().trim();
    }

    private Iterable<String> getExpectedKeys() {
        return translations.keySet();
    }

    @Override
    public String getName() {
        return "Translation generator";
    }
}