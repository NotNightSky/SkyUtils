package net.notnightsky.skyutils.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
import net.notnightsky.skyutils.modules.zoom.zoomHelper;
import net.notnightsky.skyutils.utils.DeltaTime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @ModifyReturnValue(at = @At("RETURN"), method = "projectHorizonToScreen()D")
    private double onGetFov(double original) {
        return zoomHelper.changeFovOnZoom(original);
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void skyutils$updateZoom(DeltaTracker tickCounter, boolean tick, CallbackInfo ci) {
        DeltaTime.update();
        zoomHelper.tick();
    }
}
