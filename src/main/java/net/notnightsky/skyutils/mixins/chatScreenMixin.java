package net.notnightsky.skyutils.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.network.chat.Component;
import net.notnightsky.skyutils.config.modConfig;
//import net.notnightsky.skyutils.modules.coords.chatHudCurrentCoords.chatCurrentCoords;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public class chatScreenMixin {
    @Inject(method = "init", at = @At("TAIL"))
    private void skyutils$addButtons(CallbackInfo ci) {
        if (modConfig.hookChatScreen) {
            ChatScreen screen = (ChatScreen)(Object)this;
//            int buttonWidth = 120;
//            int buttonHeight = 20;
//            int screenWidth = screen.width;
//            int screenHeight = screen.height;
//            int bottomRightX = screenWidth - buttonWidth - 2;
//            int bottomRightY = screenHeight - buttonHeight - 15;
//
//            chatCurrentCoords currentCoordsButton = new chatCurrentCoords(bottomRightX, bottomRightY, buttonWidth, buttonHeight);
            screen.addRenderableWidget(Button.builder(Component.translatable("ChatCoords"), button -> {
                Minecraft client = Minecraft.getInstance();
                if (client.player != null) {
                    String coords = String.format("%.0f, %.0f, %.0f",
                            client.player.getX(), client.player.getY(), client.player.getZ());
                    client.keyboardHandler.setClipboard(coords);
                    client.player.sendOverlayMessage(Component.translatable("ChatCoords"));

                    button.setFocused(false);
                }
            }).bounds(screen.width - 120 - 2, screen.height - 20 - 15, 120, 20).build());
        }
    }
}