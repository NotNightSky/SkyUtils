package net.notnightsky.skyutils.gui.hud.elements;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.notnightsky.skyutils.gui.hud.HudElement;

public class CoordinateElement extends AbstractHudElement {
    private String getText() {
        ClientPlayerEntity player = client.player;
        if (player == null) return getPlaceholderText();
        return String.format("XYZ: %d / %d / %d",
                (int) player.getX(), (int) player.getY(), (int) player.getZ());
    }

    @Override
    public void render(DrawContext context, float deltaTicks) {
        if (client.player == null) return;
        String text = getText();
        int textWidth = client.textRenderer.getWidth(text);
        drawBackground(context, textWidth);
        drawText(context, client.textRenderer, text);
    }

    @Override public int getWidth() { return client.textRenderer.getWidth(getText()); }
    @Override public String getId() { return "skyutils:coordinates"; }
    @Override public String getPlaceholderText() { return "XYZ: 0 / 0 / 0"; }

}