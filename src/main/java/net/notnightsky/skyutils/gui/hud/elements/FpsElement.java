package net.notnightsky.skyutils.gui.hud.elements;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.notnightsky.skyutils.gui.hud.HudElement;

public class FpsElement implements HudElement {

    private final MinecraftClient client = MinecraftClient.getInstance();
    private int x = 2;
    private int y = 5;
    private boolean enabled = true;

    @Override
    public void render(DrawContext context, float deltaTicks) {
        String text = "FPS: " + client.getCurrentFps();
        int textWidth = client.textRenderer.getWidth(text);
        drawBackground(context, textWidth);
        drawText(context, client.textRenderer, text);
    }

    @Override
    public int getWidth() {return client.textRenderer.getWidth("FPS: " + client.getCurrentFps());}
    @Override public int getX() { return x; }
    @Override public int getY() { return y; }
    @Override public void setX(int x) { this.x = x; }
    @Override public void setY(int y) { this.y = y; }
    @Override public boolean isEnabled() { return enabled; }
    @Override public void setEnabled(boolean enabled) { this.enabled = enabled; }
    @Override public String getId() { return "skyutils:fps"; }
    @Override public String getPlaceholderText() { return "FPS: 60"; }
}