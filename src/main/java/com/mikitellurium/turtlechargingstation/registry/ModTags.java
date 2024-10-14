package com.mikitellurium.turtlechargingstation.registry;

import com.mikitellurium.telluriumforge.registry.InitializedRegistry;
import com.mikitellurium.telluriumforge.registry.RegistryHelper;
import com.mikitellurium.turtlechargingstation.TurtleChargingStationMod;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags implements InitializedRegistry {

    public static TagKey<Block> DYNAMO_CONDUCTIVE_BLOCKS;

    @Override
    public void init(RegistryHelper helper) {
        DYNAMO_CONDUCTIVE_BLOCKS = helper.registerTag(RegistryKeys.BLOCK, "dynamo_conductive_blocks");
    }

}
