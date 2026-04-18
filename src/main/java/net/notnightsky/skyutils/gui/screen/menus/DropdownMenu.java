package net.notnightsky.skyutils.gui.screen.menus;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Colors;

import java.util.ArrayList;
import java.util.List;

public class DropdownMenu {

    public record DropdownOption(String label, Runnable action) {}

    private final int x;
    private final int y;
    private final List<DropdownOption> options = new ArrayList<>();
    private static final int OPTION_HEIGHT = 12;
    private static final int PADDING = 4;
    private static final int WIDTH = 80;
    private final MinecraftClient client = MinecraftClient.getInstance();

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

    public void render(DrawContext context, int mouseX, int mouseY) {
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

            context.drawText(client.textRenderer, options.get(i).label(),
                    x + PADDING, optionY + 2, hovered ? Colors.WHITE : Colors.LIGHTER_GRAY, false);
        }
    }

    public boolean mouseClicked(Click click) {
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

    public boolean isClickOutside(Click click) {
        return click.x() < x || click.x() > x + WIDTH
                || click.y() < y || click.y() > y + getHeight();
    }
}
