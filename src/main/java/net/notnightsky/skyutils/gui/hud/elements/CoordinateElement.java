package net.notnightsky.skyutils.gui.hud.elements;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.notnightsky.skyutils.gui.hud.HudElement;

public class CoordinateElement extends AbstractHudElement {
    private String getText() {
        LocalPlayer player = client.player;
        if (player == null) return getPlaceholderText();
        return String.format("XYZ: %d / %d / %d",
                (int) player.getX(), (int) player.getY(), (int) player.getZ());
    }

    @Override
    public void render(GuiGraphics context, float deltaTicks) {
        if (client.player == null) return;
        String text = getText();
        int textWidth = client.font.width(text);
        drawBackground(context, textWidth);
        drawText(context, client.font, text);
    }

    @Override public int getWidth() { return client.font.width(getText()); }
    @Override public String getId() { return "skyutils:coordinates"; }
    @Override public String getPlaceholderText() { return "XYZ: 0 / 0 / 0"; }

}