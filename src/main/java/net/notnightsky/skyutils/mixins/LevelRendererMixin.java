package net.notnightsky.skyutils.mixins;


import net.minecraft.client.renderer.LevelRenderer;
import net.notnightsky.skyutils.config.modConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {

//    @WrapOperation(method = "renderTargetBlockOutline", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderTargetBlockOutline(Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/util/math/MatrixStack;ZLnet/minecraft/client/render/state/WorldRenderState;)V))
//    private void skyutils$outlineColor(WorldRenderer instance, MatrixStack matrices, VertexConsumer vertexConsumer, double x, double y, double z, OutlineRenderState state, int color, Operation<Void> original) {
//        int newI = modConfig.outlinecolor.getRGB();
//        original.call(instance, matrices, vertexConsumer, x, y, z, state, newI);
//    }
    @ModifyArgs(method = "renderBlockOutline", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderHitOutline(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;DDDLnet/minecraft/client/renderer/state/BlockOutlineRenderState;IF)V"))
    private void skyutils$outlinecolor(Args args){
        int newI = modConfig.outlinecolor.getRGB();
        args.set(6, newI);
    }
}
