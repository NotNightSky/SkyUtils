package net.notnightsky.skyutils.gui.hud;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public class HudManager {

    private static final Map<String, HudElement> elements = new LinkedHashMap<>();

    /**
     * Register a HUD element by its ID.
     */
    public static void register(HudElement element) {
        elements.put(element.getId(), element);
    }

    /**
     * Unregister a HUD element by ID.
     */
    public static void unregister(String id) {
        elements.remove(id);
    }

    /**
     * Get a registered element by ID.
     */
    public static HudElement get(String id) {
        return elements.get(id);
    }

    /**
     * Get all active elements
     */
    public static Collection<HudElement> getAll() {
        return elements.values();
    }

    /**
     * Render all enabled HUD elements.
     */
    public static void renderAll(GuiGraphicsExtractor context, float deltaTicks) {
        Minecraft client = Minecraft.getInstance();
        int screenWidth = client.getWindow().getGuiScaledWidth();
        int screenHeight = client.getWindow().getGuiScaledHeight();

        for (HudElement element : elements.values()) {
            if (element.isEnabled()) {
                element.setX(Math.clamp(element.getX(), 3, screenWidth - element.getWidth() - 3));
                element.setY(Math.clamp(element.getY(), 3, screenHeight - element.getHeight() - 3));
                element.render(context, deltaTicks);
            }
        }
    }
}