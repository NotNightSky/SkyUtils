package net.notnightsky.skyutils.gui.hud.elements;

import net.minecraft.client.gui.GuiGraphicsExtractor;

public class FpsElement extends AbstractHudElement{

    @Override
    public void render(GuiGraphicsExtractor context, float deltaTicks) {
        String text = "FPS: " + client.getFps();
        int textWidth = client.font.width(text);
        drawBackground(context, textWidth);
        drawText(context, client.font, text);
    }

    @Override public int getWidth() { return client.font.width(getPlaceholderText()); }
    @Override public String getId() { return "skyutils:fps"; }
    @Override public String getPlaceholderText() { return "FPS: 60"; }

}