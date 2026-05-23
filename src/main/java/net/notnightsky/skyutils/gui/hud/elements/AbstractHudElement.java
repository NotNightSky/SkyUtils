package net.notnightsky.skyutils.gui.hud.elements;

import net.minecraft.client.Minecraft;
import net.notnightsky.skyutils.gui.hud.HudElement;

public abstract class AbstractHudElement implements HudElement {

    protected final Minecraft client = Minecraft.getInstance();

    private int x = 0;
    private int y = 0;
    private boolean enabled = true;

    @Override public int getX() { return x; }
    @Override public int getY() { return y; }
    @Override public void setX(int x) { this.x = x; }
    @Override public void setY(int y) { this.y = y; }
    @Override public boolean isEnabled() { return enabled; }
    @Override public void setEnabled(boolean enabled) { this.enabled = enabled; }
}
