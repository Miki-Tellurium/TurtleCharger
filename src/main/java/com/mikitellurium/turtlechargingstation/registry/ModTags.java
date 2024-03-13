package com.mikitellurium.turtlechargingstation.registry;

import com.mikitellurium.telluriumforge.registry.RegistryHelper;
import com.mikitellurium.turtlechargingstation.TurtleChargingStationMod;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {

    private static final RegistryHelper registry = TurtleChargingStationMod.registryHelper();

    public static final TagKey<Block> DYNAMO_CONDUCTIVE_BLOCKS = registry.registerTag(RegistryKeys.BLOCK, "dynamo_conductive_blocks");

}
