package net.notnightsky.skyutils.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Avatar;
import net.notnightsky.skyutils.config.modConfig;
import net.notnightsky.skyutils.modules.playerhealthindicator.PlayerHealthInterface;
import net.notnightsky.skyutils.modules.playerlatency.PlayerLatencyInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.notnightsky.skyutils.utils.pingColorHelper.pingColor;

@Mixin(AvatarRenderer.class)
public class AvatarRendererMixin {

    @Unique
    private AvatarRenderState skyutils$cachedState;

    @Inject(method = "submitNameTag(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V", at = @At("HEAD"))
    private void skyutils$cacheState(AvatarRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera, CallbackInfo ci) {
        this.skyutils$cachedState = state;
        if (modConfig.showHealth || modConfig.showPing) {
            float health = ((PlayerHealthInterface) state).skyutils$getHealth();
            int latency = ((PlayerLatencyInterface) state).skyutils$getLatency();
            String color = pingColor(latency);

            Component suffix;
            if (modConfig.showHealth && !modConfig.showPing) {
                suffix = Component.literal(" §c[" + Math.round(health) + "❤] ");
            } else if (!modConfig.showHealth && modConfig.showPing) {
                suffix = Component.literal(color + "[" + latency + "]");
            } else {
                suffix = Component.literal(" §c[" + Math.round(health) + "❤] " + color + "[" + latency + "]");
            }

            if (state.nameTag != null) {
                state.nameTag = state.nameTag.copy().append(suffix);
            }
            if (state.scoreText != null) {
                state.scoreText = state.scoreText.copy().append(suffix);
            }
        }
    }

    @Inject(method = "submitNameTag(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V", at = @At("TAIL"))
    private void skyutils$restoreState(AvatarRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera, CallbackInfo ci) {
        this.skyutils$cachedState = null;
    }

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V", at = @At("TAIL"))
    private void skyutils$capHealth(Avatar entity, AvatarRenderState state, float tickDelta, CallbackInfo ci) {
        if (modConfig.showHealth) {
            ((PlayerHealthInterface) state).skyutils$setHealth(entity.getHealth());
        }
    }

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V", at = @At("TAIL"))
    private void skyutils$captureLatency(Avatar entity, AvatarRenderState state, float tickDelta, CallbackInfo ci) {
        if (modConfig.showPing) {
            if (Minecraft.getInstance().getConnection() == null) return;
            PlayerInfo entry = Minecraft.getInstance().getConnection().getPlayerInfo(entity.getUUID());
            if (entry != null) {
                ((PlayerLatencyInterface) state).skyutils$setLatency(entry.getLatency());
            }
        }
    }
}
