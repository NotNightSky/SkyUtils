package net.notnightsky.skyutils.modules.zoom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.notnightsky.skyutils.config.keyBindingHelper.keyBinding;
import net.notnightsky.skyutils.config.modConfig;
import net.notnightsky.skyutils.utils.DeltaTime;
import net.notnightsky.skyutils.utils.math;

public class zoomHelper {

    private static final double defaultZoom = modConfig.defaultZoom;
    private static final double lerpSpeed = modConfig.zoomSpeed;

    private static double baseZoom = 1.0;
    private static double scrollZoom = 1.0;
    private static double scrollVelocity = 0.0;

    private static double currentLevel = 1.0;
    private static double targetLevel  = 1.0;

    private static Double defaultMouseSensitivity;

    public static float changeFovOnZoom(float fov) {
        OptionInstance<Double> mouseSensitivitySetting = Minecraft.getInstance().options.sensitivity();
        baseZoom = keyBinding.zoomKey.isDown() ? defaultZoom : 1.0;
        if(keyBinding.zoomKey.isDown()){

            baseZoom = defaultZoom;
        } else {
            baseZoom = 1.0;
            if(defaultMouseSensitivity != null)
            {
                mouseSensitivitySetting.set(defaultMouseSensitivity);
                defaultMouseSensitivity = null;
            }
        }

        if(defaultMouseSensitivity == null)
            defaultMouseSensitivity = mouseSensitivitySetting.get();

        mouseSensitivitySetting.set(defaultMouseSensitivity * (1.0 / currentLevel));

        return (float) (fov / currentLevel);
    }

    public static void onMouseScroll(double vert) {
        if (!keyBinding.zoomKey.isDown()) return;
        scrollVelocity += vert * 2.5;
    }

    public static void tick() {
        double dt = DeltaTime.getSec();

        if (Math.abs(scrollVelocity) > 1e-4) {
            scrollZoom *= Math.exp(scrollVelocity * dt);
            scrollZoom = Math.clamp(scrollZoom, 1.0 / defaultZoom, 50.0 / defaultZoom);
        }

        scrollVelocity *= Math.exp(-12.0 * dt);

        if (!keyBinding.zoomKey.isDown()) {
            scrollZoom = 1.0;
        }

        targetLevel = baseZoom * scrollZoom;

        if (modConfig.interpolationType != InterpolationMode.INSTANT){
            currentLevel = math.interpolate(currentLevel, targetLevel, lerpSpeed, dt, modConfig.interpolationType);
        } else {
            currentLevel = targetLevel;
        }
    }
}
