package net.notnightsky.skyutils.hud;


import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
     * Render all enabled HUD elements.
     */
    public static void renderAll(DrawContext context, float deltaTicks) {
        for (HudElement element : elements.values()) {
            if (element.isEnabled()) {
                element.render(context, deltaTicks);
            }
        }
    }
}
