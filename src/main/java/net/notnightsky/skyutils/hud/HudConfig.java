package net.notnightsky.skyutils.hud;

public class HudConfig {
    /*This is a temporary config used to implement hud configs temporarily.
    * This in no particular manner mean that the default config is being discontinued.
    * Any further changes will be announced in the github page*/

    private static int backgroundColor = -1873784752;
    private static int textColor = net.minecraft.util.Colors.LIGHTER_GRAY;
    private static boolean textShadow = false;

    public static int getBackgroundColor() { return backgroundColor; }
    public static void setBackgroundColor(int color) { backgroundColor = color; }

    public static int getTextColor() { return textColor; }
    public static void setTextColor(int color) { textColor = color; }

    public static boolean isTextShadow() { return textShadow; }
    public static void setTextShadow(boolean shadow) { textShadow = shadow; }
}