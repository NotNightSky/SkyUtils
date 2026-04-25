package net.notnightsky.skyutils.gui.hud.elements;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.notnightsky.skyutils.gui.hud.HudElement;

public class SpeedElement extends AbstractHudElement {

    private double speed = 0;

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

    @Override public int getWidth() { return client.textRenderer.getWidth(speed == 0 ? getPlaceholderText() : getText()); }
    @Override public String getId() { return "skyutils:speed"; }
    @Override public String getPlaceholderText() { return "Speed: 4.35 b/s"; }

}