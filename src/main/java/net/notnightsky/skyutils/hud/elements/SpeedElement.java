package net.notnightsky.skyutils.hud.elements;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.math.Vec3d;
import net.notnightsky.skyutils.hud.HudElement;

public class SpeedElement implements HudElement {

    private final MinecraftClient client = MinecraftClient.getInstance();

    @Override
    public void render(DrawContext context, float deltaTicks) {
        ClientPlayerEntity player = client.player;
        if (player == null) return;

        Entity entity = player.getVehicle() != null ? player.getVehicle() : player;
        Vec3d velocity = entity.getVelocity();
        double speed = Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z) * 20;

        String text = String.format("Speed: %.2f b/s", speed);
        int textWidth = client.textRenderer.getWidth(text);

        drawBackground(context, textWidth);
        drawText(context, client.textRenderer, text);
    }

    @Override public int getX() { return 2; }
    @Override public int getY() { return 31; }
    @Override public boolean isEnabled() { return true; }
    @Override public String getId() { return "skyutils:speed"; }
}
