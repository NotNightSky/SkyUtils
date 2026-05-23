package net.notnightsky.skyutils.config.keyBindingHelper;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.notnightsky.skyutils.config.modConfig;
import net.notnightsky.skyutils.gui.screen.HudEditorScreen;

import static net.notnightsky.skyutils.config.keyBindingHelper.keyBinding.*;
import static net.notnightsky.skyutils.modules.fullbright.fullBright.*;

public class toggleHandler {

    public static void registerToggle(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(!loaded && Minecraft.getInstance().options != null) {
                loaded = true;
                if(modConfig.fullBright) {
                    enableFullbright();
                }
            }

            if (hudEditorKey.consumeClick()){
                Minecraft.getInstance().setScreen(new HudEditorScreen(Minecraft.getInstance().screen));
            }

            if (openMenu.consumeClick()) {
                Screen configScreen = modConfig.openConfigScreen(Minecraft.getInstance().screen);
                Minecraft.getInstance().setScreen(configScreen);
            }

            if (incrementFullBright.consumeClick()){
                incrementFullBright();
            }

            if (decrementFullBright.consumeClick()){
                decrementFullBright();
            }

            if (toggleFullBright.consumeClick()) {
                toggleFullbright();
            }
        });
    }

    public static void toggleFullbright() {
        if (modConfig.fullBright) {
            disableFullbright();
        } else {
            enableFullbright();
        }
        modConfig.HANDLER.save();
    }

    public static void setFullbright(boolean fullbright) {
        if (fullbright) {
            enableFullbright();
        } else {
            disableFullbright();
        }
        modConfig.HANDLER.save();
    }
}
