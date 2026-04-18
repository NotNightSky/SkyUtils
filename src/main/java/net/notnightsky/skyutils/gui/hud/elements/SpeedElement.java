package net.notnightsky.skyutils.gui.hud.elements;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.notnightsky.skyutils.gui.hud.HudElement;

public class SpeedElement implements HudElement {

    private final MinecraftClient client = MinecraftClient.getInstance();
    private int x = 2;
    private int y = 31;
    private double speed = 0;
    private boolean enabled = true;

    private String getText() {
        return String.format("Speed %.2f b/s", speed);
    }

    @Override
    public void render(DrawContext context, float deltaTicks) {
        ClientPlayerEntity player = client.player;
        if (player == null) return;

        Entity entity = player.getVehicle() != null ? player.getVehicle() : player;
        Vec3d velocity = entity.getVelocity();
        speed = Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z) * 20;

        String text = getText();
        int textWidth = client.textRenderer.getWidth(text);
        drawBackground(context, textWidth);
        drawText(context, client.textRenderer, text);
    }

    @Override
    public int getWidth() {if (speed == 0) return client.textRenderer.getWidth(getPlaceholderText());return client.textRenderer.getWidth(getText());}
    @Override public int getX() { return x; }
    @Override public int getY() { return y; }
    @Override public void setX(int x) { this.x = x; }
    @Override public void setY(int y) { this.y = y; }
    @Override public boolean isEnabled() { return enabled; }
    @Override public void setEnabled(boolean enabled) { this.enabled = enabled; }
    @Override public String getId() { return "skyutils:speed"; }
    @Override public String getPlaceholderText() { return "Speed 4.35 b/s"; }
}