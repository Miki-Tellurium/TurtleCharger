package com.mikitellurium.turtlechargingstation.registry;

import com.mikitellurium.turtlechargingstation.TurtleChargingStationMod;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {

    public static final TagKey<Block> DYNAMO_CONDUCTIVE_BLOCKS =
            TagKey.of(RegistryKeys.BLOCK, new Identifier(TurtleChargingStationMod.MOD_ID, "dynamo_conductive_blocks"));

}
