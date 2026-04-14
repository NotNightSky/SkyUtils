package net.notnightsky.skyutils.hud.screen;

import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.notnightsky.skyutils.config.modConfig;
import net.notnightsky.skyutils.hud.HudElement;
import net.notnightsky.skyutils.hud.HudManager;
import net.notnightsky.skyutils.utils.Rectangle;
import net.notnightsky.skyutils.utils.SnappingHelper;

import java.util.ArrayList;
import java.util.List;

public class HudEditorScreen extends Screen {

    private final Screen parent;
    private String dragging = null;
    private int dragOffsetX, dragOffsetY;
    private SnappingHelper snappingHelper;

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

        rects.add(new Rectangle(0, 0, width, 0));               // top edge
        rects.add(new Rectangle(0, height, width, 0));          // bottom edge
        rects.add(new Rectangle(0, 0, 0, height));              // left edge
        rects.add(new Rectangle(width, 0, 0, height));          // right edge

        for (HudElement element : HudManager.getAll()) {
            if (!element.getId().equals(excludeId)) {
                rects.add(new Rectangle(
                        element.getX(),
                        element.getY(),
                        client.textRenderer.getWidth(element.getPlaceholderText()),
                        element.getHeight()
                ));
            }
        }
        return rects;
    }

    private Rectangle getElementRect(HudElement element) {
        return new Rectangle(
                element.getX(),
                element.getY(),
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

        String title = "HUD Editor";
        String hint = "Escape to save";
        context.drawText(client.textRenderer, title,
                width / 2 - client.textRenderer.getWidth(title) / 2, 5, Colors.WHITE, false);
        context.drawText(client.textRenderer, hint,
                width / 2 - client.textRenderer.getWidth(hint) / 2, 16, Colors.LIGHTER_GRAY, false);

        super.render(context, mouseX, mouseY, delta);
    }

    private void renderPlaceholder(DrawContext context, HudElement element) {
        String text = element.getPlaceholderText();
        int textWidth = client.textRenderer.getWidth(text);
        context.fill(
                element.getX() - 1, element.getY() - 1,
                element.getX() + textWidth + 1, element.getY() + element.getHeight(),
                -1873784752
        );
        context.drawText(client.textRenderer, text, element.getX(), element.getY(), Colors.LIGHTER_GRAY, false);
    }

    private void drawHighlight(DrawContext context, HudElement element, int mouseX, int mouseY) {
        int x1 = element.getX() - 2;
        int y1 = element.getY() - 2;
        int x2 = element.getX() + client.textRenderer.getWidth(element.getPlaceholderText()) + 2;
        int y2 = element.getY() + element.getHeight() + 2;

        boolean hovered = isHovered(element, mouseX, mouseY);
        boolean isDragging = element.getId().equals(dragging);

        int borderColor = isDragging ? 0xFFFFAA00 : hovered ? 0xFFFFFFFF : 0x80FFFFFF;

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

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
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
        }
        modConfig.HANDLER.save();
    }

    @Override
    public boolean shouldPause() { return false; }
}