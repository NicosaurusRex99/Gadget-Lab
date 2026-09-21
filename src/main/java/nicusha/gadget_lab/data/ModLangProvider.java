package nicusha.gadget_lab.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import nicusha.gadget_lab.GadgetLab;
import nicusha.gadget_lab.registry.BlockRegistry;
import nicusha.gadget_lab.registry.EntityRegistry;
import nicusha.gadget_lab.registry.ItemRegistry;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModLangProvider extends LanguageProvider {
    protected final String locale;
    private final Map<String, String> existingTranslations = new HashMap<>();
    private static final Gson GSON = new Gson();

    public ModLangProvider(PackOutput output, String locale) {
        super(output, GadgetLab.MODID, locale);
        this.locale = locale;
        loadExistingTranslations(output);
    }

    @Override
    protected void addTranslations() {
        BlockRegistry.BLOCKS.getEntries().forEach(regObj -> {
            String path = regObj.getId().getPath();
            addAuto("block." + GadgetLab.MODID + "." + path, generateLocalizedNameFromPath(path));
        });

        ItemRegistry.ITEMS.getEntries().forEach(regObj -> {
            String path = regObj.getId().getPath();
            addAuto("item." + GadgetLab.MODID + "." + path, generateLocalizedNameFromPath(path));
        });

        EntityRegistry.ENTITIES.getEntries().forEach(regObj -> {
            String path = regObj.getId().getPath();
            addAuto("entity." + GadgetLab.MODID + "." + path, generateLocalizedNameFromPath(path));
        });

        // Creative Tab
        addAuto("itemGroup.gadget_lab", "Gadget Lab");

        // Item Tooltips
        addAuto("tooltip.item.gadget_lab.rebreather", "Allows you to swim faster and breath underwater for 5 minutes");
        addAuto("tooltip.item.gadget_lab.portable_crafting_table", "A portable crafting table");
        addAuto("tooltip.item.gadget_lab.enigmatic_hold", "Holds up-to 10 mobs and places them in the same order they were captured in");
        addAuto("tooltip.item.gadget_lab.enigmatic_hold.full", "Enigmatic Hold is full! (10/10)");
        addAuto("tooltip.wip.gadget_lab", "Currently does not function");
        addAuto("tooltip.item.gadget_lab.gravity_boots", "Jump higher and removes fall damage");
        addAuto("tooltip.item.gadget_lab.lucky_amulet", "Gives luck when in the players inventory");
        addAuto("tooltip.item.gadget_lab.fortune_amulet", "Gives fortune when in the players inventory");
        addAuto("tooltip.item.gadget_lab.harvesting_gloves", "Enhances crop yield");
        addAuto("tooltip.item.gadget_lab.teleportation_wand", "Teleports 32 blocks in the direction the player is looking, if there is a block between the player and the look spot it teleports just infront or on top");
        addAuto("tooltip.item.gadget_lab.pocket_watch", "Adds a hud with the game time");
        addAuto("tooltip.item.gadget_lab.climbing_gloves", "Allows you to climb 3 blocks high");
        addAuto("tooltip.item.gadget_lab.smoke_bombs", "Spawns a large area of smoke particles");
        addAuto("tooltip.item.gadget_lab.herbicide_spray", "Clears out all flora ontop of grass");
        addAuto("tooltip.item.gadget_lab.blaze_blaster", "Shoots blaze powder and creates fires");
        addAuto("tooltip.item.gadget_lab.smoke_bomb", "When thrown creates a smokescreen");
        addAuto("tooltip.item.gadget_lab.magnetic_glove", "Draws items towards the player");
        addAuto("tooltip.item.gadget_lab.invisibility_cloak", "Gives the player invisibility when worn");
        addAuto("tooltip.item.gadget_lab.launch_pad", "Launches the player fairly high");
        addAuto("tooltip.item.gadget_lab.energy_transmitter", "Wirelessly power machines in a small radius at the cost of power");
        addAuto("tooltip.item.gadget_lab.growth_accelerator", "Speeds up crop growth in a small radius");
        addAuto("tooltip.item.gadget_lab.void_chest", "Voids all items placed inside");
        addAuto("tooltip.item.gadget_lab.block_breaker", "Breaks blocks on the breaking face of the block breaker");
        addAuto("tooltip.item.gadget_lab.pedestal", "Holds items");
        addAuto("tooltip.item.gadget_lab.entity_magnet", "Attracts mobs, toggled via redstone.");
        addAuto("tooltip.item.gadget_lab.cloud_platform", "You can place this block mid air allowing you to create platforms while flying");
        addAuto("tooltip.item.gadget_lab.ore_extractor", "Mines ores directly below. Outputs above");
        addAuto("tooltip.item.gadget_lab.carbon_compressor", "Turns various carbon items into diamonds");
        addAuto("tooltip.item.gadget_lab.displacement_pad", "Teleports you to a predefined location");
        addAuto("tooltip.item.gadget_lab.holographic_projector", "Renders an entity above the block. Set via enigmatic hold");
        addAuto("tooltip.item.gadget_lab.soul_lantern", "Emits light and soul particles");
        addAuto("tooltip.item.gadget_lab.slime_siphon", "Collects slimeballs from slimes above");
        addAuto("tooltip.item.gadget_lab.quicksand", "Sand that can trap you");
        addAuto("tooltip.item.gadget_lab.fishermans_net", "Gathers regular fishing items");
        addAuto("tooltip.item.gadget_lab.sun_dial", "Tells the time");

        // Enchantments
        addAuto("enchantment.gadget_lab.magma_walker", "Magma Walker");
        addAuto("enchantment.gadget_lab.magma_walker.desc", "The Magma Walker enchantment temporarily freezes the lava around you and turns it into obsidian");
    }

    private String generateLocalizedNameFromPath(String path) {
        String[] parts = path.split("_");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (!part.isEmpty()) {
                sb.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1)).append(" ");
            }
        }
        return sb.toString().trim();
    }

    private void addAuto(Object key, String englishValue) {
        String translationKey = getTranslationKey(key);
        if (translationKey == null) return;
        if (existingTranslations.containsKey(translationKey)) {
            add(translationKey, existingTranslations.get(translationKey));
            return;
        }
        String targetText = englishValue;
        if (!locale.equals("en_us")) {
            String langCode = locale.split("_")[0];
            targetText = translate(langCode, englishValue);
        }
        add(translationKey, targetText);
    }

    private String getTranslationKey(Object key) {
        if (key instanceof Supplier<?> s) {
            Object val = s.get();
            if (val instanceof Block b) return b.getDescriptionId();
            if (val instanceof Item i) return i.getDescriptionId();
            if (val instanceof EntityType<?> e) return e.getDescriptionId();
        } else {
            if (key instanceof Block b) return b.getDescriptionId();
            if (key instanceof Item i) return i.getDescriptionId();
            if (key instanceof EntityType<?> e) return e.getDescriptionId();
            if (key instanceof String s) return s;
        }
        return null;
    }

    private void loadExistingTranslations(PackOutput output) {
        try {
            Path path = output.getOutputFolder()
                    .resolve("assets")
                    .resolve(GadgetLab.MODID)
                    .resolve("lang")
                    .resolve(locale + ".json");

            if (Files.exists(path)) {
                try (Reader reader = Files.newBufferedReader(path)) {
                    JsonObject json = GSON.fromJson(reader, JsonObject.class);
                    if (json != null) {
                        json.entrySet().forEach(entry ->
                                existingTranslations.put(entry.getKey(), entry.getValue().getAsString())
                        );
                    }
                }
            }
        } catch (Exception e) {
            System.out.printf("Could not load existing translations for %s: %s%n", locale, e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "Languages: " + GadgetLab.MODID + " (" + locale + ")";
    }

    public static String translate(String langTo, String text) {
        try {
            String urlStr = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=en&tl=" + langTo + "&dt=t&q=" + URLEncoder.encode(text, StandardCharsets.UTF_8);
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) response.append(inputLine);
            in.close();
            String result = response.toString();
            return result.substring(4, result.indexOf("\"", 4));
        } catch (Exception e) {
            return text;
        }
    }
}