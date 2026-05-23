package net.notnightsky.skyutils.gui.screen.menus;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.util.CommonColors;

public class DropdownMenu {

    public record DropdownOption(String label, Runnable action) {}

    private final int x;
    private final int y;
    private final List<DropdownOption> options = new ArrayList<>();
    private static final int OPTION_HEIGHT = 12;
    private static final int PADDING = 4;
    private static final int WIDTH = 80;
    private final Minecraft client = Minecraft.getInstance();

    public DropdownMenu(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void addOption(String label, Runnable action) {
        options.add(new DropdownOption(label, action));
    }

    public int getWidth() { return WIDTH; }
    public int getHeight() { return options.size() * OPTION_HEIGHT + PADDING * 2; }
    public int getX() { return x; }
    public int getY() { return y; }

    public void render(GuiGraphics context, int mouseX, int mouseY) {
        context.fill(x, y, x + WIDTH, y + getHeight(), 0xFF1A1A1A);
        context.fill(x, y, x + WIDTH, y + 1, 0xFF555555);
        context.fill(x, y + getHeight() - 1, x + WIDTH, y + getHeight(), 0xFF555555);
        context.fill(x, y, x + 1, y + getHeight(), 0xFF555555);
        context.fill(x + WIDTH - 1, y, x + WIDTH, y + getHeight(), 0xFF555555);

        for (int i = 0; i < options.size(); i++) {
            int optionY = y + PADDING + i * OPTION_HEIGHT;
            boolean hovered = mouseX >= x && mouseX <= x + WIDTH
                    && mouseY >= optionY && mouseY <= optionY + OPTION_HEIGHT;

            if (hovered) {
                context.fill(x + 1, optionY, x + WIDTH - 1, optionY + OPTION_HEIGHT, 0xFF2A2A2A);
            }

            context.drawString(client.font, options.get(i).label(),
                    x + PADDING, optionY + 2, hovered ? CommonColors.WHITE : CommonColors.TEXT_GRAY, false);
        }
    }

    public boolean mouseClicked(MouseButtonEvent click) {
        if (click.button() != 0) return false;
        for (int i = 0; i < options.size(); i++) {
            int optionY = y + PADDING + i * OPTION_HEIGHT;
            if (click.x() >= x && click.x() <= x + WIDTH
                    && click.y() >= optionY && click.y() <= optionY + OPTION_HEIGHT) {
                options.get(i).action().run();
                return true;
            }
        }
        return false;
    }

    public boolean isClickOutside(MouseButtonEvent click) {
        return click.x() < x || click.x() > x + WIDTH
                || click.y() < y || click.y() > y + getHeight();
    }
}
