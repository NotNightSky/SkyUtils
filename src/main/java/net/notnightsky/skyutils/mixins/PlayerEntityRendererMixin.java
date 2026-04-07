package net.notnightsky.skyutils.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.PlayerLikeEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.notnightsky.skyutils.config.modConfig;
import net.notnightsky.skyutils.modules.playerhealthindicator.PlayerHealthInterface;
import net.notnightsky.skyutils.modules.playerlatency.PlayerLatencyInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.notnightsky.skyutils.utils.pingColorHelper.pingColor;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
    @WrapOperation(method = "renderLabelIfPresent(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;submitLabel(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Vec3d;ILnet/minecraft/text/Text;ZIDLnet/minecraft/client/render/state/CameraRenderState;)V"))
    private void skyutils$modifyLabel(OrderedRenderCommandQueue queue, MatrixStack matrices, Vec3d pos, int light, Text text, boolean isSneaking, int backgroundColor, double squaredDistance, CameraRenderState camera, Operation<Void> original, PlayerEntityRenderState state) {
        float health = ((PlayerHealthInterface) state).skyutils$getHealth();
        int latency = ((PlayerLatencyInterface) state).skyutils$getLatency();

        String color = pingColor(latency);

        Text modified;
        if (modConfig.showHealth && !modConfig.showPing) {
            modified = text.copy().append(" §c[" + Math.round(health) + "❤] ");
        } else if (!modConfig.showHealth && modConfig.showPing) {
            modified = text.copy().append(color + "[" + latency + "]");
        } else if (modConfig.showPing && modConfig.showHealth) {
            modified = text.copy().append(" §c[" + Math.round(health) + "❤] " + color + "[" + latency + "]");
        } else {
            modified = text.copy();
        }
        original.call(queue, matrices, pos, light, modified, isSneaking, backgroundColor, squaredDistance, camera);
    }


    @Inject(method = "updateRenderState(Lnet/minecraft/entity/PlayerLikeEntity;Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;F)V", at = @At("TAIL"))
    private void skyutils$capHealth(PlayerLikeEntity entity, PlayerEntityRenderState state, float tickDelta, CallbackInfo ci) {
        if (modConfig.showHealth) {
            PlayerHealthInterface access = (PlayerHealthInterface) state;

            access.skyutils$setHealth(entity.getHealth());
        }
    }


    @Inject(method = "updateRenderState(Lnet/minecraft/entity/PlayerLikeEntity;Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;F)V", at = @At("TAIL"))
    private void skyutils$captureLatency(PlayerLikeEntity entity, PlayerEntityRenderState state, float tickDelta, CallbackInfo ci) {
        if (modConfig.showPing) {
            PlayerLatencyInterface access = (PlayerLatencyInterface) state;

            if(MinecraftClient.getInstance().getNetworkHandler() == null){
                return;
            }

            PlayerListEntry entry = MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(entity.getUuid());

            if (entry != null) {
                access.skyutils$setLatency(entry.getLatency());
            }
        }
    }
}
