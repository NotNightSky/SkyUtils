package net.notnightsky.skyutils.modules.fullbright;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.notnightsky.skyutils.config.modConfig;

public class fullBright {
    public static void enableFullbright(){
        Minecraft.getInstance().options.gamma().set(modConfig.gamma / 100.0);
        if (Minecraft.getInstance().player != null){
            Minecraft.getInstance().player.sendOverlayMessage(Component.nullToEmpty("Gamma Value is Set to " + (int)modConfig.gamma + "%"));
        }
        modConfig.fullBright = true;
    }

    public static void disableFullbright(){
        Minecraft.getInstance().options.gamma().set(modConfig.defaultGamma / 100.0);
        if (Minecraft.getInstance().player != null){
            Minecraft.getInstance().player.sendOverlayMessage(Component.nullToEmpty("Gamma Value is Set to " + (int)modConfig.defaultGamma + "%"));
        }
        modConfig.fullBright = false;
    }

    public static void incrementFullBright(){
        if(modConfig.fullBright){
            if( !(modConfig.gamma > 1000.0 || modConfig.gamma + modConfig.increment > 1000.0)) {
                modConfig.gamma = modConfig.gamma + modConfig.increment;
                enableFullbright();
            } else if (modConfig.gamma <= 1000.0){
                modConfig.gamma = 1000.0;
                if (Minecraft.getInstance().player != null){
                    Minecraft.getInstance().player.sendOverlayMessage(Component.nullToEmpty("Gamma Value Reached 1000%"));
                }
            }
            modConfig.HANDLER.save();
        }
    }

    public static void decrementFullBright(){
        if(modConfig.fullBright){
            if( !(modConfig.gamma < 0.0 || modConfig.gamma - modConfig.increment < 0.0)) {
                modConfig.gamma = modConfig.gamma - modConfig.decrement;
                enableFullbright();
            } else if (modConfig.gamma < 0.0){
                modConfig.gamma = 0.0;
                if (Minecraft.getInstance().player != null){
                    Minecraft.getInstance().player.sendOverlayMessage(Component.nullToEmpty("Gamma Value Reached 1%"));
                }
            }
            modConfig.HANDLER.save();
        }
    }

    public void noDarknessEffect(){
        if (modConfig.nodarkness){
            if (Minecraft.getInstance().player != null) {
                MobEffectInstance darknessEffect = Minecraft.getInstance().player.getEffect(MobEffects.DARKNESS);
                if (darknessEffect != null) {
                    Minecraft.getInstance().player.removeEffect(MobEffects.DARKNESS);
                }
            }
        }
    }
}
