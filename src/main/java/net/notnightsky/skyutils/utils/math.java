package net.notnightsky.skyutils.utils;

import net.notnightsky.skyutils.modules.zoom.InterpolationMode;

public final class math {

    public static double interpolate(double current, double target, double speed, double deltaTime, InterpolationMode mode) {
        if (mode == InterpolationMode.INSTANT || speed <= 0.0) {
            return target;
        }

        double t = smoothingFactor(speed, deltaTime);

        t = switch (mode) {
            case LINEAR -> t;
            case LOGARITHMIC -> t;
            case EASEINOUTSINE -> easeInOutSine(t);
            case EASEOUTCUBIC -> easeOutCubic(t);
            case EASEOUTQUAD -> easeOutQuad(t);
            case EASEOUTEXPO -> easeOutExpo(t);
            default -> t;
        };

        if (mode == InterpolationMode.LOGARITHMIC) {
            return lerpLog(current, target, t);
        }

        return lerp(current, target, t);
    }

    private static double smoothingFactor(double speed, double dt) {
        return 1.0 - Math.exp(-speed * dt);
    }

    public static double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }

    private static double lerpLog(double a, double b, double t) {
        a = Math.max(a, 1e-6);
        b = Math.max(b, 1e-6);
        return Math.exp(lerp(Math.log(a), Math.log(b), t));
    }

    private static double easeInOutSine(double t) {
        return -(Math.cos(Math.PI * t) - 1.0) * 0.5;
    }

    private static double easeOutCubic(double t) {
        return 1.0 - Math.pow(1.0 - t, 3.0);
    }

    private static double easeOutQuad(double t) {
        return 1.0 - (1.0 - t) * (1.0 - t);
    }

    private static double easeOutExpo(double t) {
        return t >= 1.0 ? 1.0 : 1.0 - Math.pow(2.0, -10.0 * t);
    }

    public static boolean withinBox(int x, int y, int w, int h, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= w && mouseY >= y && mouseY <= y + h;
    }
}
