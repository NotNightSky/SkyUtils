package net.notnightsky.skyutils.hud.elements;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.notnightsky.skyutils.hud.HudElement;

public class FpsElement implements HudElement {

    private final MinecraftClient client = MinecraftClient.getInstance();

    @Override
    public void render(DrawContext context, float deltaTicks) {
        String text = "FPS: " + client.getCurrentFps();
        int textWidth = client.textRenderer.getWidth(text);

        drawBackground(context, textWidth);
        drawText(context, client.textRenderer, text);
    }

    @Override public int getX() { return 2; }
    @Override public int getY() { return 5; }
    @Override public boolean isEnabled() { return true; }
    @Override public String getId() { return "skyutils:fps"; }
}
