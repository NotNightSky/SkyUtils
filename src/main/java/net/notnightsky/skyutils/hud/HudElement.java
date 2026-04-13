package net.notnightsky.skyutils.hud;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public interface HudElement {
    void render(DrawContext context, float deltaTicks);
    int getX();
    int getY();
    boolean isEnabled();
    String getId();

    // Optional per-element overrides, fall back to global by default
    default int getBackgroundColor() { return HudConfig.backgroundColor; }
    default int getTextColor() { return HudConfig.textColor; }
    default boolean hasShadow() { return HudConfig.textShadow; }

    // Shared helper so every element doesn't repeat this logic
    default void drawBackground(DrawContext context, int textWidth) {
        context.fill(
                getX() - 1, getY() - 1,
                getX() + textWidth + 1, getY() + 8,
                getBackgroundColor()
        );
    }

    default void drawText(DrawContext context, TextRenderer textRenderer, String text) {
        context.drawText(textRenderer, text, getX(), getY(), getTextColor(), hasShadow());
    }
}
