package net.notnightsky.skyutils.config.keyBindingHelper;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.notnightsky.skyutils.SkyutilsClient;
import org.lwjgl.glfw.GLFW;

public class keyBinding {
    public static KeyMapping openMenu;
    public static KeyMapping toggleFullBright;
    public static KeyMapping incrementFullBright;
    public static KeyMapping decrementFullBright;
    public static KeyMapping zoomKey;
    public static KeyMapping hudEditorKey;
    public static boolean loaded = false;

    public static void registerKeybinds(){
        KeyMapping.Category skyutilsCategory = KeyMapping.Category.register(Identifier.parse("skyutils"));
        openMenu = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.skyutils.openMenu",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                skyutilsCategory
        ));

        zoomKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.skyutils.zoom",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                skyutilsCategory
        ));

        toggleFullBright = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.skyutils.toggleFullBright",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_ALT,
                skyutilsCategory
        ));

        incrementFullBright = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.skyutils.incrementFullBright",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_KP_ADD,
                skyutilsCategory
        ));

        decrementFullBright = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.skyutils.decrementFullBright",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_KP_SUBTRACT,
                skyutilsCategory
        ));

        if (SkyutilsClient.isDevEnv){
            hudEditorKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                    "key.skyutils.hudEditorKey",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_H,
                    skyutilsCategory
            ));
        }
    }
}
