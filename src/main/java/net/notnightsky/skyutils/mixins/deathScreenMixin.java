package net.notnightsky.skyutils.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.network.chat.Component;
import net.notnightsky.skyutils.config.modConfig;
//import net.notnightsky.skyutils.modules.coords.deathCoords.deathCoordWiget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DeathScreen.class)
public abstract class deathScreenMixin {
    @Inject(method = "init", at = @At("TAIL"))
    private void skyutils$addDeathCoordButton(CallbackInfo ci) {
        if (modConfig.hookDeathScreen){
            DeathScreen screen = (DeathScreen)(Object)this;
            screen.addRenderableWidget(Button.builder(Component.translatable("DeathCoords"), button -> {
                Minecraft client = Minecraft.getInstance();
                if (client.player != null) {
                    String coords = String.format("%.0f, %.0f, %.0f",
                            client.player.getX(), client.player.getY(), client.player.getZ());
                    client.keyboardHandler.setClipboard(coords);
                    client.player.displayClientMessage(Component.literal("Death coordinates copied to clipboard"), false);

                    button.setFocused(false);
                }
            }).bounds(screen.width / 2 - 59, screen.height /4 + 48, 120, 20).build());
            //screen.width / 2 - 59, screen.height / 4 + 48, 120, 20
        }
    }
}