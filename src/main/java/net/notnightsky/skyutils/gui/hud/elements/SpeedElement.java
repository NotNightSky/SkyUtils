package net.notnightsky.skyutils.gui.hud.elements;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.notnightsky.skyutils.gui.hud.HudElement;

public class SpeedElement extends AbstractHudElement {

    private double speed = 0;

    private String getText() {
        return String.format("Speed %.2f b/s", speed);
    }

    @Override
    public void render(GuiGraphics context, float deltaTicks) {
        LocalPlayer player = client.player;
        if (player == null) return;

        Entity entity = player.getVehicle() != null ? player.getVehicle() : player;
        Vec3 velocity = entity.getDeltaMovement();
        speed = Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z) * 20;

        String text = getText();
        int textWidth = client.font.width(text);
        drawBackground(context, textWidth);
        drawText(context, client.font, text);
    }

    @Override public int getWidth() { return client.font.width(speed == 0 ? getPlaceholderText() : getText()); }
    @Override public String getId() { return "skyutils:speed"; }
    @Override public String getPlaceholderText() { return "Speed: 4.35 b/s"; }

}