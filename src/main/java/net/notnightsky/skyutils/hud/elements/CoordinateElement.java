package net.notnightsky.skyutils.hud.elements;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.notnightsky.skyutils.hud.HudElement;

public class CoordinateElement implements HudElement {

    private final MinecraftClient client = MinecraftClient.getInstance();

    @Override
    public void render(DrawContext context, float deltaTicks) {
        ClientPlayerEntity player = client.player;
        if (player == null) return;

        String text = String.format("XYZ: %d / %d / %d",
                (int) player.getX(), (int) player.getY(), (int) player.getZ());
        int textWidth = client.textRenderer.getWidth(text);

        drawBackground(context, textWidth);
        drawText(context, client.textRenderer, text);
    }

    @Override public int getX() { return 2; }
    @Override public int getY() { return 18; }
    @Override public boolean isEnabled() { return true; }
    @Override public String getId() { return "skyutils:coordinates"; }
}
