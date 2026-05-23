package net.notnightsky.skyutils.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.notnightsky.skyutils.config.modConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Gui.class)
public class GuiMixin {
    @WrapOperation(method = "renderCameraOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderTextureOverlay(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/resources/Identifier;F)V"))
    private void modifyIfPumpkin(Gui instance, GuiGraphics context, Identifier texture, float opacity, Operation<Void> original) {
        float modifiedOpacity = (float) (modConfig.pumpkinOverlayOpacity / 100);
        Player player = Minecraft.getInstance().player;

        if(player == null){
            return;
        }

        if (modConfig.pumpkinOverlay && player.getItemBySlot(EquipmentSlot.HEAD).is(Items.CARVED_PUMPKIN)) {
            original.call(instance, context, texture, modifiedOpacity);
        } else {
            original.call(instance, context, texture, opacity);
        }
    }
}
