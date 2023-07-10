package com.mikitellurium.turtlecharginstation.datagen;

import com.mikitellurium.turtlecharginstation.TurtleChargingStationMod;
import com.mikitellurium.turtlecharginstation.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModTagsProvider extends BlockTagsProvider {

    public ModTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                           @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TurtleChargingStationMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ModTags.DYNAMO_CONDUCTIVE_BLOCKS)
                .add(Blocks.CHAIN)
                .add(Blocks.COPPER_BLOCK);
    }

}
