package net.notnightsky.skyutils.mixins;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import net.minecraft.client.OptionInstance;

@Mixin(OptionInstance.UnitDouble.class)

//Mixin taken from https://github.com/Greeenman999/fullbright/blob/35b0e26baf58d9cc1a94c599f0391c084570f96b/src/main/java/de/greenman999/fullbright/mixin/DoubleSliderCallbacksMixin.java

public class doubleSliderCallBackMixin {
    @Inject(method = "validateValue(Ljava/lang/Double;)Ljava/util/Optional;", at = @At("RETURN"), cancellable = true)
    public void skyutils$removeBrightnessValidation(Double double_, CallbackInfoReturnable<Optional<Double>> cir) {
        cir.setReturnValue(double_ == null ? Optional.empty() : Optional.of(double_));
    }

}
