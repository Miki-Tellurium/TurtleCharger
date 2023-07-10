package com.mikitellurium.turtlecharginstation.registry;

import com.mikitellurium.turtlecharginstation.TurtleChargingStationMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static final TagKey<Block> DYNAMO_CONDUCTIVE_BLOCKS =
            BlockTags.create(new ResourceLocation(TurtleChargingStationMod.MOD_ID, "dynamo_conductive_blocks"));



}
