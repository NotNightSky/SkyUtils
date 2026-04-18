package net.notnightsky.skyutils.config.keyBindingHelper;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.notnightsky.skyutils.config.modConfig;
import net.notnightsky.skyutils.gui.screen.HudEditorScreen;

import static net.notnightsky.skyutils.config.keyBindingHelper.keyBinding.*;
import static net.notnightsky.skyutils.modules.fullbright.fullBright.*;

public class toggleHandler {

    public static void registerToggle(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(!loaded && MinecraftClient.getInstance().options != null) {
                loaded = true;
                if(modConfig.fullBright) {
                    enableFullbright();
                }
            }

            if (hudEditorKey.wasPressed()){
                MinecraftClient.getInstance().setScreen(new HudEditorScreen(MinecraftClient.getInstance().currentScreen));
            }

            if (openMenu.wasPressed()) {
                Screen configScreen = modConfig.openConfigScreen(MinecraftClient.getInstance().currentScreen);
                MinecraftClient.getInstance().setScreen(configScreen);
            }

            if (incrementFullBright.wasPressed()){
                incrementFullBright();
            }

            if (decrementFullBright.wasPressed()){
                decrementFullBright();
            }

            if (toggleFullBright.wasPressed()) {
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
