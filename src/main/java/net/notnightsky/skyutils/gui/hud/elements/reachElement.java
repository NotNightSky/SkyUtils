
package net.notnightsky.skyutils.gui.hud.elements;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class reachElement extends AbstractHudElement {

    private static final long DISPLAY_DURATION_MS = 2000;

    private int decimalPlaces = 2;
    private String currentDist = null;
    private long lastTime = 0;

    public reachElement() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!world.isClientSide()) return InteractionResult.PASS;

            double distance = getAttackDistance(player, entity);
            StringBuilder format = new StringBuilder("0");
            if (decimalPlaces > 0) {
                format.append(".");
                format.append("0".repeat(decimalPlaces));
            }
            DecimalFormat formatter = new DecimalFormat(format.toString());
            formatter.setRoundingMode(RoundingMode.HALF_UP);

            currentDist = formatter.format(distance);
            lastTime = System.currentTimeMillis();

            return InteractionResult.PASS;
        });
    }

    private String getText() {
        if (currentDist == null) return "0 blocks";
        if (System.currentTimeMillis() > lastTime + DISPLAY_DURATION_MS) {
            currentDist = null;
            return "0 blocks";
        }
        return currentDist + " blocks";
    }

    @Override
    public void render(GuiGraphics context, float deltaTicks) {
        if (client.player == null) return;
        String text = getText();
        int textWidth = client.font.width(text);
        drawBackground(context, textWidth);
        drawText(context, client.font, text);
    }

    /**
     * apparently something called signed distance field is perfect
     * for detecting collision. so this maybe accurate not really sure though
     * https://iquilezles.org/articles/distfunctions/
     */
    private static double getAttackDistance(Entity attacking, Entity receiving) {
        AABB bb = receiving.getBoundingBox();

        double rx = (bb.maxX - bb.minX) / 2.0;
        double ry = (bb.maxY - bb.minY) / 2.0;
        double rz = (bb.maxZ - bb.minZ) / 2.0;

        double cx = (bb.minX + bb.maxX) / 2.0;
        double cy = (bb.minY + bb.maxY) / 2.0;
        double cz = (bb.minZ + bb.maxZ) / 2.0;

        Vec3 eye = attacking.getEyePosition();
        double px = eye.x - cx;
        double py = eye.y - cy;
        double pz = eye.z - cz;

        double qx = Math.abs(px) - rx;
        double qy = Math.abs(py) - ry;
        double qz = Math.abs(pz) - rz;

        double outer = Math.sqrt(
                Math.pow(Math.max(qx, 0), 2) +
                        Math.pow(Math.max(qy, 0), 2) +
                        Math.pow(Math.max(qz, 0), 2)
        );
        double inner = Math.clamp(Math.max(qx, Math.max(qy, qz)), Double.NEGATIVE_INFINITY, 0);

        return Math.max(outer + inner, 0);
    }

    @Override public int getWidth() { return client.font.width(currentDist != null ? currentDist + " blocks" : getPlaceholderText()); }
    @Override public String getId() { return "skyutils:reach"; }
    @Override public String getPlaceholderText() { return "Reach: 3.45 blocks"; }
}
