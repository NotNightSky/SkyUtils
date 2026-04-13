package net.notnightsky.skyutils.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.notnightsky.skyutils.SkyutilsClient;
import net.notnightsky.skyutils.config.keyBindingHelper.toggleHandler;
import net.notnightsky.skyutils.modules.discordRpc.IPCManager;
import net.notnightsky.skyutils.modules.zoom.InterpolationMode;

import java.awt.*;

public class modConfig {
    private static final String LOCAL_NAMESPACE_PATH = "skyutils.yacl.";

    public static ConfigClassHandler<modConfig> HANDLER = ConfigClassHandler.createBuilder(modConfig.class)
            .id(Identifier.of("skyutils"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("skyutils.json5"))
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry
    public static InterpolationMode interpolationType = InterpolationMode.LOGARITHMIC;

    @SerialEntry
    public static double zoomSpeed = 12.0;

    @SerialEntry
    public static double defaultZoom = 3.0;

    @SerialEntry
    public static double pumpkinOverlayOpacity = 0;

    @SerialEntry
    public static double shieldTranslate = 0.23;

    @SerialEntry
    public static double fireTranslate = 0.38;

    @SerialEntry
    public static boolean showHealth = false;

    @SerialEntry
    public static boolean showPing = false;

    @SerialEntry
    public static Color outlinecolor = new Color(255, 255, 255,255);

    @SerialEntry
    public static boolean IPCEnabled = false;

    @SerialEntry
    public static String IPCplayText = "Playing Minecraft Using SkyUtils!";

    @SerialEntry
    public static boolean lowFire = false;

    @SerialEntry
    public static boolean lowShield = false;

    @SerialEntry
    public static boolean pumpkinOverlay = false;

    @SerialEntry
    public static boolean fullBright = false;

    @SerialEntry
    public static double defaultGamma = 50.0;

    @SerialEntry
    public static double gamma = 500.0;

    @SerialEntry
    public static double increment = 50.0;

    @SerialEntry
    public static double decrement = 50.0;

    @SerialEntry
    public static boolean nodarkness  = false;

    @SerialEntry
    public static boolean hookDeathScreen = false;

    @SerialEntry
    public static boolean hookChatScreen = false;

    @SerialEntry
    public static boolean furnaceToolTip = false;

    public static Screen openConfigScreen(Screen parent) {
        var builder = YetAnotherConfigLib.createBuilder()
                .title(Text.literal("SkyUtils"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "group.world"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.showping.name"))
                                .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.showping.description")))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.enableoption"))
                                        .binding(false, () -> modConfig.showPing, newVal -> modConfig.showPing = newVal)
                                        .controller((Option<Boolean> opt) -> BooleanControllerBuilder.create(opt)
                                                .trueFalseFormatter()
                                                .coloured(true))
                                        .build())
                                .collapsed(true)
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.showhealth.name"))
                                .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.showhealth.description")))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.enableoption"))
                                        .binding(false, () -> modConfig.showHealth, newVal -> modConfig.showHealth = newVal)
                                        .controller((Option<Boolean> opt) -> BooleanControllerBuilder.create(opt)
                                                .trueFalseFormatter()
                                                .coloured(true))
                                        .build())
                                .collapsed(true)
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.outlinecolor.name"))
                                .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.outlinecolor.description")))
                                .option(Option.<Color>createBuilder()
                                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.coloroption"))
                                        .binding(new Color(255, 255, 255, 255), () -> modConfig.outlinecolor, newVal -> modConfig.outlinecolor = newVal)
                                        .controller(opt -> ColorControllerBuilder.create(opt)
                                                .allowAlpha(true))
                                        .build())
                                .collapsed(true)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "group.render"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable(LOCAL_NAMESPACE_PATH + "group.zoom"))
                                .collapsed(true)
                                .option(Option.<Double>createBuilder()
                                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.defaultZoom.name"))
                                        .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.defaultZoom.description")))
                                        .binding(3.0, () -> modConfig.defaultZoom, newVal -> modConfig.defaultZoom = newVal)
                                        .controller(opt -> DoubleFieldControllerBuilder.create(opt).range(1.0,50.0))
                                        .build())
                                .option(Option.<Double>createBuilder()
                                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.zoomSpeed.name"))
                                        .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.zoomSpeed.description")))
                                        .binding(12.0, () -> modConfig.zoomSpeed, newVal -> modConfig.zoomSpeed = newVal)
                                        .controller(opt -> DoubleFieldControllerBuilder.create(opt).range(1.0,50.0))
                                        .build())
                                .option(Option.<InterpolationMode>createBuilder()
                                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.interpolationType.name"))
                                        .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.interpolationType.description")))
                                        .binding(InterpolationMode.LOGARITHMIC, () -> modConfig.interpolationType, newVal -> modConfig.interpolationType = newVal)
                                        .controller(opt -> EnumControllerBuilder.create(opt)
                                                .enumClass(InterpolationMode.class)
                                                .formatValue(v -> Text.translatable(LOCAL_NAMESPACE_PATH + "interpolationType." + v.name().toLowerCase())))
                                        .build())
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable( LOCAL_NAMESPACE_PATH + "group.fullBright"))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.fullBright.name"))
                                        .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.fullBright.description")))
                                        .binding(false, () -> modConfig.fullBright, toggleHandler::setFullbright)
                                        .controller((Option<Boolean> opt) -> BooleanControllerBuilder.create(opt)
                                                .trueFalseFormatter()
                                                .coloured(true))
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.gamma.nodarkness"))
                                        .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.gamma.nodarkness.description")))
                                        .binding(false, () -> modConfig.nodarkness, newVal -> modConfig.nodarkness = newVal)
                                        .controller((Option<Boolean> opt) -> BooleanControllerBuilder.create(opt)
                                                .trueFalseFormatter()
                                                .coloured(true))
                                        .build())
                                .option(Option.<Double>createBuilder()
                                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.gamma.name"))
                                        .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.gamma.description")))
                                        .binding(100.0, () -> modConfig.gamma, newVal -> modConfig.gamma = newVal)
                                        .controller((Option<Double> opt) -> DoubleSliderControllerBuilder.create(opt)
                                                .range(100.0, 1000.0)
                                                .step(100.0)
                                                .formatValue((Double val) -> Text.literal(val.intValue() + "%")))
                                        .build())
                                .option(Option.<Double>createBuilder()
                                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.gamma.default-Gamma.name"))
                                        .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.gamma.default-Gamma.description")))
                                        .binding(50.0, () -> modConfig.defaultGamma, newVal -> modConfig.defaultGamma = newVal)
                                        .controller((Option<Double> opt) -> DoubleSliderControllerBuilder.create(opt)
                                                .range(0.0, 100.0)
                                                .step(10.0)
                                                .formatValue((Double val) -> Text.literal(val.intValue() + "%")))
                                        .build())
                                .option(Option.<Double>createBuilder()
                                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.gamma.increment"))
                                        .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.gamma.increment.description")))
                                        .binding(50.0, () -> modConfig.increment, newVal -> modConfig.increment = newVal)
                                        .controller((Option<Double> opt) -> DoubleSliderControllerBuilder.create(opt)
                                                .range(10.0, 100.0)
                                                .step(10.0)
                                                .formatValue((Double val) -> Text.literal(val.intValue() + "%")))
                                        .build())
                                .option(Option.<Double>createBuilder()
                                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.gamma.decrement"))
                                        .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.gamma.decrement.description")))
                                        .binding(50.0, () -> modConfig.decrement, newVal -> modConfig.decrement = newVal)
                                        .controller((Option<Double> opt) -> DoubleSliderControllerBuilder.create(opt)
                                                .range(10.0, 100.0)
                                                .step(10.0)
                                                .formatValue((Double val) -> Text.literal(val.intValue() + "%")))
                                        .build())
                                .collapsed(true)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "group.overlays"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.pumpkinoverlay.name"))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.enableoption"))
                                        .binding(false, () -> modConfig.pumpkinOverlay, newVal -> modConfig.pumpkinOverlay = newVal)
                                        .controller((Option<Boolean> opt) -> BooleanControllerBuilder.create(opt)
                                                .trueFalseFormatter()
                                                .coloured(true))
                                        .build())
                                .option(Option.<Double>createBuilder()
                                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.pumpkinoverlay.opacity"))
                                        .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.pumpkinoverlay.opacity.description")))
                                        .binding(0.0, () -> modConfig.pumpkinOverlayOpacity, newVal -> modConfig.pumpkinOverlayOpacity = newVal)
                                        .controller((Option<Double> opt) -> DoubleFieldControllerBuilder.create(opt)
                                                .range(0.0, 100.0)
                                                .formatValue((Double val) -> Text.literal(val.intValue() + "%")))
                                        .build())
                                .collapsed(true)
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.lowfire.name"))
                                .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.lowfire.description")))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.enableoption"))
                                        .binding(false, () -> modConfig.lowFire, newVal -> modConfig.lowFire = newVal)
                                        .controller((Option<Boolean> opt) -> BooleanControllerBuilder.create(opt)
                                                .trueFalseFormatter()
                                                .coloured(true))
                                        .build())
                                .option(Option.<Double>createBuilder()
                                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.lowfire.translate"))
                                        .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.lowfire.translate.description")))
                                        .binding(0.38, () -> modConfig.fireTranslate, newVal -> modConfig.fireTranslate = newVal)
                                        .controller((Option<Double> opt) -> DoubleFieldControllerBuilder.create(opt)
                                                .range(0.0, 0.5))
                                        .build())
                                .collapsed(true)
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.lowshield.name"))
                                .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.lowshield.description")))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.enableoption"))
                                        .binding(false, () -> modConfig.lowShield, newVal -> modConfig.lowShield = newVal)
                                        .controller((Option<Boolean> opt) -> BooleanControllerBuilder.create(opt)
                                                .trueFalseFormatter()
                                                .coloured(true))
                                        .build())
                                .option(Option.<Double>createBuilder()
                                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.lowshield.translate"))
                                        .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.lowshield.translate.description")))
                                        .binding(0.23, () -> modConfig.shieldTranslate, newVal -> modConfig.shieldTranslate = newVal)
                                        .controller((Option<Double> opt) -> DoubleFieldControllerBuilder.create(opt)
                                                .range(0.0, 0.5))
                                        .build())
                                .collapsed(true)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "category.information"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.deathcoords.name"))
                                .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.deathcoords.description")))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.enableoption"))
                                        .binding(false, () -> modConfig.hookDeathScreen, newVal -> modConfig.hookDeathScreen = newVal)
                                        .controller((Option<Boolean> opt) -> BooleanControllerBuilder.create(opt)
                                                .trueFalseFormatter()
                                                .coloured(true))
                                        .build())
                                .collapsed(true)
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.chatcoords.name"))
                                .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.chatcoords.description")))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.enableoption"))
                                        .binding(false, () -> modConfig.hookChatScreen, newVal -> modConfig.hookChatScreen = newVal)
                                        .controller((Option<Boolean> opt) -> BooleanControllerBuilder.create(opt)
                                                .trueFalseFormatter()
                                                .coloured(true))
                                        .build())
                                .collapsed(true)
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.tooltip.furnace.name"))
                                .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.tooltip.furnace.description")))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.enableoption"))
                                        .binding(false, () -> modConfig.furnaceToolTip, newVal -> modConfig.furnaceToolTip = newVal)
                                        .controller((Option<Boolean> opt) -> BooleanControllerBuilder.create(opt)
                                                .trueFalseFormatter()
                                                .coloured(true))
                                        .build())
                                .collapsed(true)
                                .build())

                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "group.discord"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.ipc.name"))
                                .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.ipc.description")))
                                .binding(false, () -> modConfig.IPCEnabled, newVal -> modConfig.IPCEnabled = newVal)
                                .controller((Option<Boolean> opt) -> BooleanControllerBuilder.create(opt).trueFalseFormatter().coloured(true))
                                .build())
                        .option(Option.<String>createBuilder().
                                name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.ipcplaytext.name"))
                                .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.ipcplaytext.description")))
                                .binding("Playing Minecraft Using SkyUtils!", () -> modConfig.IPCplayText, newVal -> modConfig.IPCplayText = newVal)
                                .controller(StringControllerBuilder::create)
                                .build())
                        .build())
                .save(() -> {
                    modConfig.HANDLER.save();
                    IPCManager.reload();
                });

        if(SkyutilsClient.isDevEnv){
            builder.category(ConfigCategory.createBuilder()
                            .name(Text.translatable("group.dev.name"))
                            .tooltip(Text.translatable("group.dev.tooltip"))
                            .option(ButtonOption.createBuilder()
                                    .name(Text.literal("Hud Editor"))
                                    .action((yaclScreen, thisOption) -> {
//                                        MinecraftClient.getInstance().setScreen(new HudEditorScreen(openConfigScreen(parent)));
                                    })
                                    .build())
                    .build());
        }

        return builder.build().generateScreen(parent);
    }
}
