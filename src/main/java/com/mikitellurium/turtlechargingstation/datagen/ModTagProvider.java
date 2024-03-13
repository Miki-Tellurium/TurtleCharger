package com.mikitellurium.turtlechargingstation.datagen;

import com.mikitellurium.turtlechargingstation.registry.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModTagProvider extends FabricTagProvider.BlockTagProvider {

    public ModTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        this.getOrCreateTagBuilder(ModTags.DYNAMO_CONDUCTIVE_BLOCKS)
                .add(Blocks.CHAIN)
                .add(Blocks.GOLD_BLOCK)
                .add(Blocks.COPPER_BLOCK)
                .add(Blocks.CUT_COPPER)
                .add(Blocks.WAXED_COPPER_BLOCK)
                .add(Blocks.WAXED_CUT_COPPER)
                .add(Blocks.LIGHTNING_ROD);
    }

}
