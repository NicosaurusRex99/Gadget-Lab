package nicusha.gadget_lab.events;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screens.LoadingDotsText;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import nicusha.gadget_lab.Main;

@OnlyIn(Dist.CLIENT)
public class PocketWatchEvent extends Gui {
    Minecraft mc;

    public PocketWatchEvent() {
        super(Minecraft.getInstance(), Minecraft.getInstance().getItemRenderer());
        this.mc = Minecraft.getInstance();
    }

    @SubscribeEvent
    public void renderGameOverlayEvent(RenderGuiOverlayEvent.Post event) {
        Gui ui = mc.gui;

        int windowWidth = this.mc.getWindow().getGuiScaledWidth();
        int windowHeight = this.mc.getWindow().getGuiScaledHeight();
        int yLocation = 10;
        int xLocation = windowWidth / 2;

        if (mc.player.inventory.contains(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Main.MODID, "pocket_watch")).getDefaultInstance())) {
            GuiComponent.drawCenteredString(event.getPoseStack(), mc.font, time(mc.level), xLocation, yLocation, 0xFFFF00);
        }
    }

    private String time(Level level) {
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
