package net.notnightsky.skyutils.gui.hud;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.util.CommonColors;

public interface HudElement {
    void render(GuiGraphicsExtractor context, float deltaTicks);
    int getX();
    int getY();
    void setX(int x);
    void setY(int y);
    int getWidth();
    boolean isEnabled();
    void setEnabled(boolean enabled);
    String getId();
    String getPlaceholderText();

    default int getHeight() { return 9; }

    default void drawBackground(GuiGraphicsExtractor context, int textWidth) {
        context.fill(
                getX() - 1, getY() - 1,
                getX() + textWidth + 1, getY() + getHeight(),
                -1873784752
        );
    }

    default void drawText(GuiGraphicsExtractor context, Font textRenderer, String text) {
        context.text(textRenderer, text, getX(), getY(), CommonColors.TEXT_GRAY, false);
    }
}