package net.notnightsky.skyutils.gui.hud.elements;

import net.minecraft.client.gui.DrawContext;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IrlTimeElement extends AbstractHudElement{
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    @Override
    public void render(DrawContext context, float deltaTicks) {
        String text = timeFormat.format(new Date());
        int textWidth = client.textRenderer.getWidth(text);
        drawBackground(context, textWidth);
        drawText(context, client.textRenderer, text);
    }

    @Override public int getWidth() { return client.textRenderer.getWidth(getPlaceholderText()); }
    @Override public String getId() { return "skyutils:irltime"; }
    @Override public String getPlaceholderText() { return "Time: 12:00:00"; }
}
