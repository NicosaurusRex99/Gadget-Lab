package nicusha.gadget_lab.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import nicusha.gadget_lab.registry.ItemRegistry;

public class PocketWatchEvent {

    @SubscribeEvent
    public static void renderGameOverlayEvent(RenderGuiLayerEvent.Post event) {
        if (!event.getName().equals(VanillaGuiLayers.HOTBAR)) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null || mc.level == null) {
            return;
        }
        boolean hasWatch = player.getInventory().contains(stack -> stack.is(ItemRegistry.pocket_watch.get()));
        if (hasWatch) {
            String timeText = getTimeString(mc.level);
            int textColor = isDaytime(mc.level) ? 0xFFFFFF00 : 0xFF04D8F9;
            GuiGraphicsExtractor guiGraphics = event.getGuiGraphics();
            int windowWidth = mc.getWindow().getGuiScaledWidth();
            int textWidth = mc.font.width(timeText);
            int xLocation = (windowWidth / 2) - (textWidth / 2);
            int yLocation = 10;
            guiGraphics.text(mc.font, timeText, xLocation, yLocation, textColor, true);
        }
    }

    private static boolean isDaytime(Level level) {
        long time = level.getDefaultClockTime() % 24000;
        return time >= 0 && time < 12000;
    }

    private static String getTimeString(Level level) {
        long time = level.getDefaultClockTime() % 24000;
        int hour = (int) ((time / 1000) + 6) % 24;
        int minute = (int) ((time % 1000) * 60 / 1000);
        String period = hour >= 12 ? "PM" : "AM";
        int displayHour = hour % 12;
        if (displayHour == 0) {
            displayHour = 12;
        }
        return String.format("%d:%02d %s", displayHour, minute, period);
    }
}