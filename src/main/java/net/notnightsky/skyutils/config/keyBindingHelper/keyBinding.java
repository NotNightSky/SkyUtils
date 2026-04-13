package net.notnightsky.skyutils.config.keyBindingHelper;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;

import net.notnightsky.skyutils.SkyutilsClient;
import org.lwjgl.glfw.GLFW;

public class keyBinding {
    public static KeyBinding openMenu;
    public static KeyBinding toggleFullBright;
    public static KeyBinding incrementFullBright;
    public static KeyBinding decrementFullBright;
    public static KeyBinding zoomKey;
    public static KeyBinding hudEditorKey;
    public static boolean loaded = false;

    public static void registerKeybinds(){
        KeyBinding.Category skyutilsCategory = KeyBinding.Category.create(Identifier.of("skyutils"));
        openMenu = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.skyutils.openMenu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                skyutilsCategory
        ));

        zoomKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.skyutils.zoom",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                skyutilsCategory
        ));

        toggleFullBright = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.skyutils.toggleFullBright",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_ALT,
                skyutilsCategory
        ));

        incrementFullBright = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.skyutils.incrementFullBright",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_KP_ADD,
                skyutilsCategory
        ));

        decrementFullBright = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.skyutils.decrementFullBright",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_KP_SUBTRACT,
                skyutilsCategory
        ));

        if (SkyutilsClient.isDevEnv){
            hudEditorKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                    "key.skyutils.hudEditorKey",
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_H,
                    skyutilsCategory
            ));
        }
    }
}
