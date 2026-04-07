package net.notnightsky.skyutils.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
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
            screen.addDrawableChild(ButtonWidget.builder(Text.translatable("DeathCoords"), button -> {
                MinecraftClient client = MinecraftClient.getInstance();
                if (client.player != null) {
                    String coords = String.format("%.0f, %.0f, %.0f",
                            client.player.getX(), client.player.getY(), client.player.getZ());
                    client.keyboard.setClipboard(coords);
                    client.player.sendMessage(Text.literal("Death coordinates copied to clipboard"), false);

                    button.setFocused(false);
                }
            }).dimensions(screen.width / 2 - 59, screen.height /4 + 48, 120, 20).build());
            //screen.width / 2 - 59, screen.height / 4 + 48, 120, 20
        }
    }
}