package net.notnightsky.skyutils.gui.screen;

import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.notnightsky.skyutils.config.modConfig;
import net.notnightsky.skyutils.gui.hud.HudElement;
import net.notnightsky.skyutils.gui.hud.HudManager;
import net.notnightsky.skyutils.gui.screen.menus.DropdownMenu;
import net.notnightsky.skyutils.utils.Rectangle;
import net.notnightsky.skyutils.utils.SnappingHelper;


import java.util.ArrayList;
import java.util.List;

public class HudEditorScreen extends Screen {

    private final Screen parent;
    private String dragging = null;
    private int dragOffsetX, dragOffsetY;
    private SnappingHelper snappingHelper;
    private DropdownMenu activeDropdown = null;

    public HudEditorScreen(Screen parent) {
        super(Text.literal("HUD Editor"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        snappingHelper = new SnappingHelper(getSnapRects(null), new Rectangle(0, 0, 0, 0));
    }

    private List<Rectangle> getSnapRects(String excludeId) {
        List<Rectangle> rects = new ArrayList<>();
        rects.add(new Rectangle(0, 0, width, 0));
        rects.add(new Rectangle(0, height, width, 0));
        rects.add(new Rectangle(0, 0, 0, height));
        rects.add(new Rectangle(width, 0, 0, height));
        for (HudElement element : HudManager.getAll()) {
            if (!element.getId().equals(excludeId)) {
                rects.add(new Rectangle(
                        element.getX(), element.getY(),
                        client.textRenderer.getWidth(element.getPlaceholderText()),
                        element.getHeight()
                ));
            }
        }
        return rects;
    }

    private Rectangle getElementRect(HudElement element) {
        return new Rectangle(
                element.getX(), element.getY(),
                client.textRenderer.getWidth(element.getPlaceholderText()),
                element.getHeight()
        );
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, width, height, 0x80000000);

        for (HudElement element : HudManager.getAll()) {
            renderPlaceholder(context, element);
            drawHighlight(context, element, mouseX, mouseY);
        }

        if (dragging != null) {
            snappingHelper.renderSnaps(context);
        }

        // Render dropdown on top of everything
        if (activeDropdown != null) {
            activeDropdown.render(context, mouseX, mouseY);
        }

        String title = "HUD Editor";
        String hint = "Drag to reposition and Right-click to open options";
        context.drawText(client.textRenderer, title,
                width / 2 - client.textRenderer.getWidth(title) / 2, 5, Colors.WHITE, false);
        context.drawText(client.textRenderer, hint,
                width / 2 - client.textRenderer.getWidth(hint) / 2, 16, Colors.LIGHTER_GRAY, false);

        super.render(context, mouseX, mouseY, delta);
    }

    private void renderPlaceholder(DrawContext context, HudElement element) {
        String text = element.getPlaceholderText();
        int textWidth = client.textRenderer.getWidth(text);

        // Gray out disabled elements
        int bgColor = element.isEnabled() ? -1873784752 : 0x80333333;
        int textColor = element.isEnabled() ? Colors.LIGHTER_GRAY : 0xFF666666;

        context.fill(
                element.getX() - 1, element.getY() - 1,
                element.getX() + textWidth + 1, element.getY() + element.getHeight(),
                bgColor
        );
        context.drawText(client.textRenderer, text, element.getX(), element.getY(), textColor, false);

        if (!element.isEnabled()) {
            String disabledLabel = "[disabled]";
            context.drawText(client.textRenderer, disabledLabel,
                    element.getX(), element.getY() + element.getHeight() + 2, 0xFF666666, false);
        }
    }

    private void drawHighlight(DrawContext context, HudElement element, int mouseX, int mouseY) {
        int x1 = element.getX() - 2;
        int y1 = element.getY() - 2;
        int x2 = element.getX() + client.textRenderer.getWidth(element.getPlaceholderText()) + 2;
        int y2 = element.getY() + element.getHeight() + 2;

        boolean hovered = isHovered(element, mouseX, mouseY);
        boolean isDragging = element.getId().equals(dragging);

        int borderColor = !element.isEnabled() ? 0x80FF4444 : isDragging ? 0xFFFFAA00 : hovered ? 0xFFFFFFFF : 0x80FFFFFF;

        context.fill(x1, y1, x2, y1 + 1, borderColor);
        context.fill(x1, y2 - 1, x2, y2, borderColor);
        context.fill(x1, y1, x1 + 1, y2, borderColor);
        context.fill(x2 - 1, y1, x2, y2, borderColor);
    }

    private boolean isHovered(HudElement element, double mouseX, double mouseY) {
        int textWidth = client.textRenderer.getWidth(element.getPlaceholderText());
        return mouseX >= element.getX() - 2
                && mouseX <= element.getX() + textWidth + 2
                && mouseY >= element.getY() - 2
                && mouseY <= element.getY() + element.getHeight() + 2;
    }

    private int clampX(HudElement element, int x) {
        int elementWidth = Math.max(
                client.textRenderer.getWidth(element.getPlaceholderText()),
                element.getWidth()
        );
        return Math.clamp(x, 3, width - elementWidth - 3);
    }

    private int clampY(HudElement element, int y) {
        return Math.clamp(y, 3, height - element.getHeight() - 3);
    }

    private void openDropdown(HudElement element, int x, int y) {
        DropdownMenu dropdown = new DropdownMenu(x, y);
        dropdown.addOption(element.isEnabled() ? "Disable" : "Enable", () -> {
            element.setEnabled(!element.isEnabled());
            activeDropdown = null;
        });
        activeDropdown = dropdown;
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        if (activeDropdown != null) {
            if (activeDropdown.isClickOutside(click)) {
                activeDropdown = null;
            } else {
                activeDropdown.mouseClicked(click);
                return true;
            }
        }

        if (click.button() == 1) {
            for (HudElement element : HudManager.getAll()) {
                if (isHovered(element, click.x(), click.y())) {
                    openDropdown(element, (int) click.x(), (int) click.y());
                    return true;
                }
            }
        }

        if (click.button() == 0) {
            for (HudElement element : HudManager.getAll()) {
                if (isHovered(element, click.x(), click.y())) {
                    dragging = element.getId();
                    dragOffsetX = (int) click.x() - element.getX();
                    dragOffsetY = (int) click.y() - element.getY();
                    snappingHelper = new SnappingHelper(
                            getSnapRects(element.getId()),
                            getElementRect(element)
                    );
                    return true;
                }
            }
        }

        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean mouseDragged(Click click, double deltaX, double deltaY) {
        if (dragging != null) {
            HudElement element = HudManager.get(dragging);
            if (element != null) {
                int newX = clampX(element, (int) click.x() - dragOffsetX);
                int newY = clampY(element, (int) click.y() - dragOffsetY);

                snappingHelper.setCurrent(new Rectangle(newX, newY,
                        client.textRenderer.getWidth(element.getPlaceholderText()),
                        element.getHeight()
                ));

                Integer snappedX = snappingHelper.getSnappedX();
                Integer snappedY = snappingHelper.getSnappedY();

                element.setX(snappedX != null ? clampX(element, snappedX) : newX);
                element.setY(snappedY != null ? clampY(element, snappedY) : newY);
            }
            return true;
        }
        return super.mouseDragged(click, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(Click click) {
        if (click.button() == 0) {
            dragging = null;
            return true;
        }
        return super.mouseReleased(click);
    }

    @Override
    public void close() {
        savePositions();
        client.setScreen(parent);
    }

    private void savePositions() {
        for (HudElement element : HudManager.getAll()) {
            modConfig.setHudPosition(element.getId(), element.getX(), element.getY());
            modConfig.setHudElementEnabled(element.getId(), element.isEnabled());
        }
        modConfig.HANDLER.save();
    }

    @Override
    public boolean shouldPause() { return false; }
}