package net.notnightsky.skyutils.gui.hud.elements;

import java.text.SimpleDateFormat;
import java.util.Date;
import net.minecraft.client.gui.GuiGraphics;

public class IrlTimeElement extends AbstractHudElement{
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    @Override
    public void render(GuiGraphics context, float deltaTicks) {
        String text = timeFormat.format(new Date());
        int textWidth = client.font.width(text);
        drawBackground(context, textWidth);
        drawText(context, client.font, text);
    }

    @Override public int getWidth() { return client.font.width(getPlaceholderText()); }
    @Override public String getId() { return "skyutils:irltime"; }
    @Override public String getPlaceholderText() { return "Time: 12:00:00"; }
}
