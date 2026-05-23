package net.notnightsky.skyutils.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.item.ItemDisplayContext;
import net.notnightsky.skyutils.config.modConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.renderer.special.ShieldSpecialRenderer.class)
public class ShieldSpecialRenderer {

    @Inject(method = "submit(Lnet/minecraft/core/component/DataComponentMap;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;IIZI)V", at = @At(value = "HEAD", target = "Lnet/minecraft/client/renderer/special/ShieldSpecialRenderer;submit(Lnet/minecraft/core/component/DataComponentMap;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;IIZI)V"))
    private static void skyutils$lowShield(DataComponentMap componentMap, ItemDisplayContext itemDisplayContext, PoseStack matrixStack, SubmitNodeCollector orderedRenderCommandQueue, int i, int j, boolean bl, int k, CallbackInfo ci){
        if(modConfig.lowShield && itemDisplayContext.firstPerson() && itemDisplayContext.equals(ItemDisplayContext.FIRST_PERSON_LEFT_HAND) || itemDisplayContext.equals(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)){
            matrixStack.translate(0, modConfig.shieldTranslate * -1, 0);
        }
    }
}
