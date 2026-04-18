package net.notnightsky.skyutils.gui.hud.elements;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.notnightsky.skyutils.gui.hud.HudElement;

public class CoordinateElement implements HudElement {

    private final MinecraftClient client = MinecraftClient.getInstance();
    private int x = 2;
    private int y = 18;
    private boolean enabled = true;

    private String getText() {
        ClientPlayerEntity player = client.player;
        if (player == null) return "XYZ: Unavailable";
        return String.format("XYZ: %d / %d / %d",
                (int) player.getX(), (int) player.getY(), (int) player.getZ());
    }

    @Override
    public void render(DrawContext context, float deltaTicks) {
        ClientPlayerEntity player = client.player;
        if (player == null) return;
        String text = getText();
        int textWidth = client.textRenderer.getWidth(text);
        drawBackground(context, textWidth);
        drawText(context, client.textRenderer, text);
    }

    @Override
    public int getWidth() {ClientPlayerEntity player = client.player;if (player == null) return client.textRenderer.getWidth(getPlaceholderText());return client.textRenderer.getWidth(getText());}
    @Override public int getX() { return x; }
    @Override public int getY() { return y; }
    @Override public void setX(int x) { this.x = x; }
    @Override public void setY(int y) { this.y = y; }
    @Override public boolean isEnabled() { return enabled; }
    @Override public void setEnabled(boolean enabled) { this.enabled = enabled; }
    @Override public String getId() { return "skyutils:coordinates"; }
    @Override public String getPlaceholderText() { return "XYZ: 0 / 0 / 0"; }
}