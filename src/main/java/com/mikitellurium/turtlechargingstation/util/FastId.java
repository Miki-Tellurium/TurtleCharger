package com.mikitellurium.turtlechargingstation.util;

import com.mikitellurium.telluriumforge.registry.IdentifierProvider;
import com.mikitellurium.turtlechargingstation.TurtleChargingStationMod;
import net.minecraft.util.Identifier;

public class FastId implements IdentifierProvider {

    private final static FastId INSTANCE = new FastId();

    private FastId() {}

    @Override
    public String modId() {
        return TurtleChargingStationMod.MOD_ID;
    }

    public static Identifier modId(String path) {
        return FastId.INSTANCE.modIdentifier(path);
    }

    public static Identifier mcId(String path) {
        return FastId.INSTANCE.mcIdentifier(path);
    }

}
