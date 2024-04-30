package nicusha.gadget_lab.events;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import nicusha.gadget_lab.registry.ItemRegistry;

public class PocketWatchEvent{

    @SubscribeEvent
    static  void renderGameOverlayEvent(RenderGuiLayerEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (mc.player == null)
            return;
        int windowWidth = mc.getWindow().getGuiScaledWidth();
        int yLocation = 10;
        int xLocation = windowWidth / 2;
        int textColor = 0xFFFF00;

        if (mc.player.getInventory().contains(ItemRegistry.pocket_watch.get().getDefaultInstance())) {
            if (isDaytime(mc.level)) {
                textColor = 0xFFFF00;
            } else {
                textColor = 0x04D8F9;
            }

            event.getGuiGraphics().drawCenteredString(mc.font, time(mc.level), xLocation, yLocation, textColor);
        }
    }
    private static boolean isDaytime(Level level) {
        long time = level.getDayTime() % 24000;
        return time >= 0 && time < 12000;
    }
    private static String time(Level level) {
        long time;

        if (level.getDayTime() > 24000)
            time = level.getDayTime() - 24000 * (int) (level.getDayTime() / 24000);
        else
            time = level.getDayTime();

        int hour, minute;

        if ((((int) time / 1000) + 6) > 23)
            hour = (((int) time / 1000) + 6) - 24;
        else
            hour = (((int) time / 1000) + 6);

        if (((time * 60) / 1000) > 60)
            minute = (int) ((time * 60) / 1000) - (60 * ((int) time / 1000));
        else
            minute = (int) ((time * 60) / 1000);

        String period;
        if (hour >= 12) {
            period = "PM";
            if (hour > 12) {
                hour -= 12;
            }
        } else {
            period = "AM";
            if (hour == 0) {
                hour = 12;
            }
        }

        return hour + ":" + String.format("%02d", minute) + " " + period;
    }

}
