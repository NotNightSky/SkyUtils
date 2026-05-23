package net.notnightsky.skyutils.modules.furnace;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.network.chat.Component;

public class furnaceGui {
    private static final int PROGRESS_ARROW_X = 79;
    private static final int PROGRESS_ARROW_Y = 34;
    private static final int PROGRESS_ARROW_WIDTH = 24;
    private static final int PROGRESS_ARROW_HEIGHT = 17;

    public static void renderTooltipIfHovered(GuiGraphicsExtractor ctx, AbstractFurnaceScreen<?> screen, furnaceCalculations.FurnaceInfo info, int mouseX, int mouseY) {
        int screenX = screen.leftPos;
        int screenY = screen.topPos;

        if (isMouseOverArea(mouseX, mouseY, screenX, screenY)) {
            List<Component> tooltip = createTooltip(info);
            ctx.setComponentTooltipForNextFrame(Minecraft.getInstance().font, tooltip, mouseX, mouseY);
        }
    }

    private static boolean isMouseOverArea(int mouseX, int mouseY, int screenX, int screenY) {
        return mouseX >= screenX + PROGRESS_ARROW_X &&
               mouseX <= screenX + PROGRESS_ARROW_X + PROGRESS_ARROW_WIDTH &&
               mouseY >= screenY + PROGRESS_ARROW_Y &&
               mouseY <= screenY + PROGRESS_ARROW_Y + PROGRESS_ARROW_HEIGHT;
    }

    private static List<Component> createTooltip(furnaceCalculations.FurnaceInfo info) {
        List<Component> tooltip = new ArrayList<>();

        tooltip.add(Component.literal("Remaining: " + info.remainingTimeString()));
        tooltip.add(Component.literal("Total: " + info.totalTimeString()));
        tooltip.add(Component.literal("Fuel Left: " + info.fuelLeftString()));
        tooltip.add(Component.literal("Cook %: " + info.cookPercentString()));
        tooltip.add(Component.literal("Fuel %: " + info.fuelPercentString()));

        return tooltip;
    }

}
