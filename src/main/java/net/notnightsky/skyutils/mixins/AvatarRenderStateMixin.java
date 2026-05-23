package net.notnightsky.skyutils.mixins;

import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.notnightsky.skyutils.modules.playerhealthindicator.PlayerHealthInterface;
import net.notnightsky.skyutils.modules.playerlatency.PlayerLatencyInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AvatarRenderState.class)
public class AvatarRenderStateMixin implements PlayerHealthInterface, PlayerLatencyInterface {
    @Unique
    private int skyutils$latency;

    @Unique
    private float skyutils$health;

    @Override
    public float skyutils$getHealth() {
        return skyutils$health;
    }

    @Override
    public void skyutils$setHealth(float health) {
        this.skyutils$health = health;
    }

    @Override
    public int skyutils$getLatency() {
        return skyutils$latency;
    }

    @Override
    public void skyutils$setLatency(int latency) {
        skyutils$latency = latency;
    }
}
