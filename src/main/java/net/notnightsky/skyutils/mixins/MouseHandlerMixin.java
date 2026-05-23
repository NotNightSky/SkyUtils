package net.notnightsky.skyutils.mixins;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.MouseHandler;
import net.minecraft.world.entity.player.Inventory;
import net.notnightsky.skyutils.config.keyBindingHelper.keyBinding;
import net.notnightsky.skyutils.modules.zoom.zoomHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
    @Inject(method = "onScroll", at = @At(value = "RETURN"))
    private void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci){
        zoomHelper.onMouseScroll(vertical);
    }
    @WrapWithCondition(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;setSelectedSlot(I)V"), method = "onScroll")
    private boolean wrapOnMouseScroll(Inventory instance, int slot) {
        return !keyBinding.zoomKey.isDown();
    }

}
