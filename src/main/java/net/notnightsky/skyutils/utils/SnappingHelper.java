package net.notnightsky.skyutils.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public class SnappingHelper {

    private static final int SNAP_DISTANCE = 4;
    private static final int LINE_COLOR = 0xFF5555FF; // blue snap lines

    private final HashSet<Integer> x = new HashSet<>();
    private final HashSet<Integer> y = new HashSet<>();
    private Rectangle current;
    private final Minecraft client = Minecraft.getInstance();

    public SnappingHelper(List<Rectangle> rects, Rectangle current) {
        this.current = current;
        addAllRects(rects);
    }

    public void setCurrent(Rectangle current) {
        this.current = current;
    }

    public void addAllRects(List<Rectangle> rects) {
        for (Rectangle rect : rects) {
            addRect(rect);
        }
    }

    public void addRect(Rectangle rect) {
        x.add(rect.x());
        x.add(rect.x() + rect.width());
        y.add(rect.y());
        y.add(rect.y() + rect.height());
    }


    private static Optional<Integer> getNearby(int pos, HashSet<Integer> set) {
        for (Integer val : set) {
            if (val - SNAP_DISTANCE <= pos && val + SNAP_DISTANCE >= pos) {
                return Optional.of(val);
            }
        }
        return Optional.empty();
    }

    private Integer getHalfXSnap() {
        int center = client.getWindow().getGuiScaledWidth() / 2;
        int pos = current.x() + Math.round((float) current.width() / 2);
        return Math.abs(center - pos) <= SNAP_DISTANCE ? center : null;
    }

    private Integer getHalfYSnap() {
        int center = client.getWindow().getGuiScaledHeight() / 2;
        int pos = current.y() + Math.round((float) current.height() / 2);
        return Math.abs(center - pos) <= SNAP_DISTANCE ? center : null;
    }

    public Integer getSnappedX() {
        Integer snap = getNearby(current.x(), x).orElse(null);
        if (snap != null) return snap;

        snap = getNearby(current.x() + current.width(), x).orElse(null);
        if (snap != null) return snap - current.width();

        snap = getHalfXSnap();
        if (snap != null) return snap - (current.width() / 2);

        return null;
    }

    public Integer getSnappedY() {
        Integer snap = getNearby(current.y(), y).orElse(null);
        if (snap != null) return snap;

        snap = getNearby(current.y() + current.height(), y).orElse(null);
        if (snap != null) return snap - current.height();

        snap = getHalfYSnap();
        if (snap != null) return snap - (current.height() / 2);

        return null;
    }

    private Integer getRawXSnap() {
        Integer snap = getNearby(current.x(), x).orElse(null);
        if (snap != null) return snap;

        snap = getNearby(current.x() + current.width(), x).orElse(null);
        if (snap != null) return snap;

        snap = getHalfXSnap();
        if (snap != null) return snap;

        return null;
    }

    private Integer getRawYSnap() {
        Integer snap = getNearby(current.y(), y).orElse(null);
        if (snap != null) return snap;

        snap = getNearby(current.y() + current.height(), y).orElse(null);
        if (snap != null) return snap;

        snap = getHalfYSnap();
        if (snap != null) return snap;

        return null;
    }

    public void renderSnaps(GuiGraphicsExtractor context) {
        Integer snapX = getRawXSnap();
        Integer snapY = getRawYSnap();

        if (snapX != null) {
            context.fill(snapX, 0, snapX + 1, client.getWindow().getGuiScaledHeight(), LINE_COLOR);
        }
        if (snapY != null) {
            context.fill(0, snapY, client.getWindow().getGuiScaledWidth(), snapY + 1, LINE_COLOR);
        }
    }
}
