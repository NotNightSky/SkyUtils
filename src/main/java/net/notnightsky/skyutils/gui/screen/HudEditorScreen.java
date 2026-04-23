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
import net.notnightsky.skyutils.utils.math;


import java.util.ArrayList;
import java.util.List;

public class HudEditorScreen extends Screen {

    private final Screen parent;
    private String dragging = null;
    private int dragOffsetX, dragOffsetY;
    private SnappingHelper snappingHelper;
    private DropdownMenu activeDropdown = null;

    private boolean panelOpen = false;
    private final int panelWidth = 150;
    private String draggingFromPanel = null;
    private int panelButtonX, panelButtonY, panelButtonWidth = 10, panelButtonHeight;

    public HudEditorScreen(Screen parent) {
        super(Text.literal("HUD Editor"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        snappingHelper = new SnappingHelper(getSnapRects(null), new Rectangle(0, 0, 0, 0));
        panelButtonX = width - panelButtonWidth;
        panelButtonHeight = 40;
        panelButtonY = height / 2 - panelButtonHeight / 2;
    }

    private List<Rectangle> getSnapRects(String excludeId) {
        List<Rectangle> rects = new ArrayList<>();
        rects.add(new Rectangle(0, 0, width, 0));
        rects.add(new Rectangle(0, height, width, 0));
        rects.add(new Rectangle(0, 0, 0, height));
        rects.add(new Rectangle(width, 0, 0, height));
        if (panelOpen) {
            rects.add(new Rectangle(width - panelWidth - 2, 0, panelWidth + 2, height));
        }
        for (HudElement element : HudManager.getAll()) {
            if (!element.getId().equals(excludeId) && element.isEnabled()) {
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

        if (panelOpen) {
            renderPanel(context, mouseX, mouseY);
        } else {
            renderPanelButton(context, mouseX, mouseY);
        }

        for (HudElement element : HudManager.getAll()) {
            if (element.isEnabled() && !element.getId().equals(dragging)) {
                renderPlaceholder(context, element);
                drawHighlight(context, element, mouseX, mouseY);
            }
        }

        if (dragging != null) {
            snappingHelper.renderSnaps(context);
            HudElement draggingElement = HudManager.get(dragging);
            if (draggingElement != null) {
                renderPlaceholder(context, draggingElement);
                drawHighlight(context, draggingElement, mouseX, mouseY);
            }
        }

        if (draggingFromPanel != null) {
            HudElement draggingElement = HudManager.get(draggingFromPanel);
            if (draggingElement != null) {
                draggingElement.setX(clampX(draggingElement, mouseX - 50));
                draggingElement.setY(clampY(draggingElement, mouseY - 8));
                renderPlaceholder(context, draggingElement);
                drawHighlight(context, draggingElement, mouseX, mouseY);
            }
        }

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

    private void renderPanelButton(DrawContext context, int mouseX, int mouseY) {
        boolean hovered = mouseX >= panelButtonX && mouseX <= panelButtonX + panelButtonWidth
                && mouseY >= panelButtonY && mouseY <= panelButtonY + panelButtonHeight;
        int bgColor = hovered ? 0xFF3A3A3A : 0xFF1A1A1A;
        int outlineColor = 0xFF555555;

        context.fill(panelButtonX, panelButtonY, panelButtonX + panelButtonWidth, panelButtonY + panelButtonHeight, bgColor);
        context.fill(panelButtonX - 1, panelButtonY - 1, panelButtonX, panelButtonY + panelButtonHeight + 1, outlineColor);
        context.fill(panelButtonX + panelButtonWidth, panelButtonY - 1, panelButtonX + panelButtonWidth + 1, panelButtonY + panelButtonHeight + 1, outlineColor);

        String text = ">";
        int textWidth = client.textRenderer.getWidth(text);
        context.drawText(client.textRenderer, text, panelButtonX + (panelButtonWidth - textWidth) / 2, panelButtonY + (panelButtonHeight - 8) / 2, Colors.WHITE, false);
    }

    private void renderPanel(DrawContext context, int mouseX, int mouseY) {
        int panelX = width - panelWidth;
        context.fill(panelX, 0, width, height, 0xAA1A1A1A);
        context.fill(panelX - 1, 0, panelX, height, 0xAA555555);

        String title = "Available Elements";
        context.drawText(client.textRenderer, title, panelX + 5, 5, Colors.WHITE, false);

        int y = 25;
        for (HudElement element : HudManager.getAll()) {
            if (!element.isEnabled()) {
                boolean hovered = mouseX >= panelX + 2 && mouseX <= width - 2 && mouseY >= y && mouseY <= y + 16;
                int bgColor = hovered ? 0xFF3A3A3A : 0xFF2A2A2A;
                int outlineColor = hovered ? 0xFF888888 : 0xFF444444;

                context.fill(panelX + 2, y, width - 2, y + 16, bgColor);
                context.fill(panelX + 2, y, width - 2, y + 1, outlineColor);
                context.fill(panelX + 2, y + 15, width - 2, y + 16, outlineColor);
                context.fill(panelX + 2, y, panelX + 3, y + 16, outlineColor);

                context.drawText(client.textRenderer, element.getPlaceholderText(), panelX + 5, y + 3, Colors.LIGHTER_GRAY, false);

                y += 20;
            }
        }

        if (y == 25) {
            context.drawText(client.textRenderer, "None", panelX + 5, 25, Colors.GRAY, false);
        }

        String closeText = "< Close";
        boolean closeHovered = mouseX >= panelX + 5 && mouseX <= panelX + 50 && mouseY >= height - 25 && mouseY <= height - 10;
        context.drawText(client.textRenderer, closeText, panelX + 5, height - 25, closeHovered ? Colors.WHITE : Colors.GRAY, false);
    }

    private void renderPlaceholder(DrawContext context, HudElement element) {
        String text = element.getPlaceholderText();
        int textWidth = client.textRenderer.getWidth(text);

        int bgColor = -1873784752;
        int textColor = Colors.LIGHTER_GRAY;

        context.fill(
                element.getX() - 1, element.getY() - 1,
                element.getX() + textWidth + 1, element.getY() + element.getHeight(),
                bgColor
        );
        context.drawText(client.textRenderer, text, element.getX(), element.getY(), textColor, false);
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
        int maxX = panelOpen ? width - panelWidth - elementWidth - 3 : width - elementWidth - 3;
        return Math.clamp(x, 3, maxX);
    }

    private int clampY(HudElement element, int y) {
        return Math.clamp(y, 3, height - element.getHeight() - 3);
    }

    private void openDropdown(HudElement element, int x, int y) {
        DropdownMenu dropdown = new DropdownMenu(x, y);
        dropdown.addOption("Disable", () -> {
            element.setEnabled(false);
            activeDropdown = null;
        });
        activeDropdown = dropdown;
    }

    private boolean isPanelButtonHovered(int mouseX, int mouseY) {
        return mouseX >= panelButtonX && mouseX <= panelButtonX + panelButtonWidth
                && mouseY >= panelButtonY && mouseY <= panelButtonY + panelButtonHeight;
    }

    private boolean isPanelCloseHovered(int mouseX, int mouseY) {
        int panelX = width - panelWidth;
        return mouseX >= panelX + 5 && mouseX <= panelX + 50 && mouseY >= height - 25 && mouseY <= height - 10;
    }

    private boolean isInPanelArea(int mouseX, int mouseY) {
        return mouseX >= width - panelWidth;
    }

    private HudElement getPanelElementAt(int mouseX, int mouseY) {
        if (!panelOpen) return null;
        int panelX = width - panelWidth;
        if (mouseX < panelX + 2 || mouseX > width - 2) return null;

        int y = 25;
        for (HudElement element : HudManager.getAll()) {
            if (!element.isEnabled()) {
                if (mouseX >= panelX + 2 && mouseX <= width - 2 && mouseY >= y && mouseY <= y + 16) {
                    return element;
                }
                y += 20;
            }
        }
        return null;
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

        int mouseX = (int) click.x();
        int mouseY = (int) click.y();

        if (panelOpen) {
            if (isPanelCloseHovered(mouseX, mouseY)) {
                panelOpen = false;
                snappingHelper = new SnappingHelper(getSnapRects(null), new Rectangle(0, 0, 0, 0));
                return true;
            }

            boolean clickedElement = false;
            for (HudElement element : HudManager.getAll()) {
                if (element.isEnabled() && isHovered(element, click.x(), click.y())) {
                    clickedElement = true;
                    break;
                }
            }

            if (!isInPanelArea(mouseX, mouseY) && !clickedElement) {
                panelOpen = false;
                snappingHelper = new SnappingHelper(getSnapRects(null), new Rectangle(0, 0, 0, 0));
            }

            if (!clickedElement) {
                HudElement panelElement = getPanelElementAt(mouseX, mouseY);
                if (panelElement != null && click.button() == 0) {
                    draggingFromPanel = panelElement.getId();
                    return true;
                }
            }
        } else {
            if (isPanelButtonHovered(mouseX, mouseY) && click.button() == 0) {
                panelOpen = true;
                snappingHelper = new SnappingHelper(getSnapRects(null), new Rectangle(0, 0, 0, 0));
                return true;
            }
        }

        if (click.button() == 1) {
            for (HudElement element : HudManager.getAll()) {
                if (element.isEnabled() && isHovered(element, click.x(), click.y())) {
                    openDropdown(element, (int) click.x(), (int) click.y());
                    return true;
                }
            }
        }

        if (click.button() == 0) {
            for (HudElement element : HudManager.getAll()) {
                if (element.isEnabled() && isHovered(element, click.x(), click.y())) {
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
        int mouseX = (int) click.x();
        int mouseY = (int) click.y();

        if (draggingFromPanel != null) {
            if (mouseX < width - panelWidth - 10) {
                HudElement element = HudManager.get(draggingFromPanel);
                if (element != null) {
                    element.setX(clampX(element, mouseX - 50));
                    element.setY(clampY(element, mouseY - 8));
                    element.setEnabled(true);
                    snappingHelper = new SnappingHelper(
                            getSnapRects(element.getId()),
                            getElementRect(element)
                    );
                }
                dragging = draggingFromPanel;
                draggingFromPanel = null;
                dragOffsetX = 50;
                dragOffsetY = 8;
            }
            return true;
        }

        if (dragging != null) {
            HudElement element = HudManager.get(dragging);
            if (element != null) {
                int newX = (int) click.x() - dragOffsetX;
                int newY = clampY(element, (int) click.y() - dragOffsetY);

                if (panelOpen && mouseX >= width - panelWidth - 30) {
                    newX = width - panelWidth - 10;
                }

                newX = clampX(element, newX);

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
            if (dragging != null && panelOpen) {
                int mouseX = (int) click.x();
                HudElement element = HudManager.get(dragging);
                if (element != null && mouseX >= width - panelWidth - 30) {
                    element.setEnabled(false);
                    panelOpen = false;
                    snappingHelper = new SnappingHelper(getSnapRects(null), new Rectangle(0, 0, 0, 0));
                }
            }
            dragging = null;
            draggingFromPanel = null;
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