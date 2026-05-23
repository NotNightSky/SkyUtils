package net.notnightsky.skyutils.mixins;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.ContainerData;
import net.notnightsky.skyutils.config.modConfig;
import net.notnightsky.skyutils.modules.furnace.furnaceCalculations;
import net.notnightsky.skyutils.modules.furnace.furnaceGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceScreen.class)
public abstract class furnaceScreenMixin {

    @Inject(method = "extractBackground", at = @At("TAIL"))
    private void skyutils$furnaceUtil(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a, CallbackInfo ci) {
        if (modConfig.furnaceToolTip){
            AbstractFurnaceScreen<?> screen = (AbstractFurnaceScreen<?>) (Object) this;
            AbstractFurnaceMenu handler = screen.getMenu();

            ContainerData props = handler.data;

            int fuelRemaining = props.get(0);
            int fuelTime = props.get(1);
            int cookElapsed = props.get(2);
            int cookTime = props.get(3);

            int totalItemsToSmelt = 0;

            if (handler.getSlot(0) != null && !handler.getSlot(0).getItem().isEmpty()) {
                totalItemsToSmelt = handler.getSlot(0).getItem().getCount();
            }

            furnaceCalculations.FurnaceInfo info = furnaceCalculations.fromProperties(fuelRemaining, fuelTime, cookElapsed, cookTime, totalItemsToSmelt, 0);

            furnaceGui.renderTooltipIfHovered(graphics, screen, info, mouseX, mouseY);
        }
    }
}