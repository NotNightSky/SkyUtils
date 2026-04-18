package net.notnightsky.skyutils.gui.hud;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Colors;

public interface HudElement {
    void render(DrawContext context, float deltaTicks);
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

    default void drawBackground(DrawContext context, int textWidth) {
        context.fill(
                getX() - 1, getY() - 1,
                getX() + textWidth + 1, getY() + getHeight(),
                -1873784752
        );
    }

    default void drawText(DrawContext context, TextRenderer textRenderer, String text) {
        context.drawText(textRenderer, text, getX(), getY(), Colors.LIGHTER_GRAY, false);
    }
}