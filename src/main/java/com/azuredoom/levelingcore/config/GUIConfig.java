package com.azuredoom.levelingcore.config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

public class GUIConfig {

    public static final BuilderCodec<GUIConfig> CODEC = BuilderCodec.builder(GUIConfig.class, GUIConfig::new)
        .append(
            new KeyedCodec<Boolean>("EnableXPLossOnDeath", Codec.BOOLEAN),
            (exConfig, aDouble, extraInfo) -> exConfig.enableXPLossOnDeath = aDouble,
            (exConfig, extraInfo) -> exConfig.enableXPLossOnDeath
        )
        .add()
        .append(
            new KeyedCodec<Double>("XPLossPercentage", Codec.DOUBLE),
            (exConfig, aDouble, extraInfo) -> exConfig.xpLossPercentage = aDouble,
            (exConfig, extraInfo) -> exConfig.xpLossPercentage
        )
        .add()
        .append(
            new KeyedCodec<Boolean>("EnableDefaultXPGainSystem", Codec.BOOLEAN),
            (exConfig, aDouble, extraInfo) -> exConfig.enableDefaultXPGainSystem = aDouble,
            (exConfig, extraInfo) -> exConfig.enableDefaultXPGainSystem
        )
        .add()
        .build();

    private boolean enableXPLossOnDeath = false;

    private double xpLossPercentage = 0.1;

    private boolean enableDefaultXPGainSystem = true;

    public GUIConfig() {}

    public double getXpLossPercentage() {
        return xpLossPercentage;
    }

    public boolean isEnableXPLossOnDeath() {
        return enableXPLossOnDeath;
    }

    public boolean isEnableDefaultXPGainSystem() {
        return enableDefaultXPGainSystem;
    }
}
