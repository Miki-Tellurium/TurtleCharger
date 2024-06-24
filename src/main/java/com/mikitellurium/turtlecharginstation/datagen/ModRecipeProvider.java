package com.mikitellurium.turtlecharginstation.datagen;

import com.mikitellurium.turtlecharginstation.TurtleChargingStationMod;
import com.mikitellurium.turtlecharginstation.registry.ModBlocks;
import com.mikitellurium.turtlecharginstation.util.FastLoc;
import com.mikitellurium.turtlecharginstation.util.ModIdConstants;
import dan200.computercraft.shared.ModRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(DataGenerator generator, CompletableFuture<HolderLookup.Provider> future) {
        super(generator.getPackOutput(), future);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        Item redstoneBlock = Items.REDSTONE_BLOCK;
        Item energyCellFrame = BuiltInRegistries.ITEM.get(FastLoc.of(ModIdConstants.ID_THERMAL, "energy_cell_frame"));
        Item machineFrame = BuiltInRegistries.ITEM.get(FastLoc.of(ModIdConstants.ID_THERMAL, "machine_frame"));
        Item rfCoil = BuiltInRegistries.ITEM.get(FastLoc.of(ModIdConstants.ID_THERMAL, "rf_coil"));
        Item steelCasing = BuiltInRegistries.ITEM.get(FastLoc.of(ModIdConstants.ID_MEKANISM, "steel_casing"));
        Item osmiumIngot = BuiltInRegistries.ITEM.get(FastLoc.of(ModIdConstants.ID_MEKANISM, "ingot_osmium"));
        Item dielectricCasing = BuiltInRegistries.ITEM.get(FastLoc.of(ModIdConstants.ID_POWAH, "dielectric_casing"));
        Item basicCapacitor = BuiltInRegistries.ITEM.get(FastLoc.of(ModIdConstants.ID_POWAH, "capacitor_basic"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TURTLE_CHARGING_STATION_BLOCK.get())
                .pattern("cgc")
                .pattern("gRg")
                .pattern("cIc")
                .define('c', Blocks.BLACK_CONCRETE)
                .define('g', Tags.Items.INGOTS_GOLD)
                .define('I', Tags.Items.STORAGE_BLOCKS_IRON)
                .define('R', redstoneBlock)
                .unlockedBy("has_turtle", has(ModRegistry.Blocks.TURTLE_NORMAL.get()))
                .unlockedBy("has_advanced_turtle", has(ModRegistry.Blocks.TURTLE_ADVANCED.get()))
                .save(recipeOutput.withConditions(not(or(
                                modLoaded(ModIdConstants.ID_THERMAL),
                                modLoaded(ModIdConstants.ID_MEKANISM),
                                modLoaded(ModIdConstants.ID_POWAH)))),
                        FastLoc.modLoc("turtle_charging_station"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TURTLE_CHARGING_STATION_BLOCK.get())
                .pattern("cgc")
                .pattern("gRg")
                .pattern("cIc")
                .define('c', Blocks.BLACK_CONCRETE)
                .define('g', Tags.Items.INGOTS_GOLD)
                .define('I', Tags.Items.STORAGE_BLOCKS_IRON)
                .define('R', energyCellFrame)
                .unlockedBy("has_turtle", has(ModRegistry.Blocks.TURTLE_NORMAL.get()))
                .unlockedBy("has_advanced_turtle", has(ModRegistry.Blocks.TURTLE_ADVANCED.get()))
                .save(recipeOutput.withConditions(and(
                        modLoaded(ModIdConstants.ID_THERMAL),
                        itemExists(ModIdConstants.ID_THERMAL, "energy_cell_frame"))),
                        FastLoc.modLoc("turtle_charging_station_thermal"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TURTLE_CHARGING_STATION_BLOCK.get())
                .pattern("cgc")
                .pattern("gRg")
                .pattern("cIc")
                .define('c', Blocks.BLACK_CONCRETE)
                .define('g', Tags.Items.INGOTS_GOLD)
                .define('I', Tags.Items.STORAGE_BLOCKS_IRON)
                .define('R', steelCasing)
                .unlockedBy("has_turtle", has(ModRegistry.Blocks.TURTLE_NORMAL.get()))
                .unlockedBy("has_advanced_turtle", has(ModRegistry.Blocks.TURTLE_ADVANCED.get()))
                .save(recipeOutput.withConditions(and(
                                modLoaded(ModIdConstants.ID_MEKANISM),
                                itemExists(ModIdConstants.ID_MEKANISM, "steel_casing"))),
                        FastLoc.modLoc("turtle_charging_station_mekanism"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TURTLE_CHARGING_STATION_BLOCK.get())
                .pattern("cgc")
                .pattern("gRg")
                .pattern("cIc")
                .define('c', Blocks.BLACK_CONCRETE)
                .define('g', Tags.Items.INGOTS_GOLD)
                .define('I', Tags.Items.STORAGE_BLOCKS_IRON)
                .define('R', dielectricCasing)
                .unlockedBy("has_turtle", has(ModRegistry.Blocks.TURTLE_NORMAL.get()))
                .unlockedBy("has_advanced_turtle", has(ModRegistry.Blocks.TURTLE_ADVANCED.get()))
                .save(recipeOutput.withConditions(and(
                                modLoaded(ModIdConstants.ID_POWAH),
                                itemExists(ModIdConstants.ID_POWAH, "dielectric_casing"))),
                        FastLoc.modLoc("turtle_charging_station_powah"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.THUNDERCHARGE_DYNAMO_BLOCK.get())
                .pattern("XRX")
                .pattern("X#X")
                .pattern("XGX")
                .define('X', Tags.Items.INGOTS_IRON)
                .define('R', Blocks.LIGHTNING_ROD)
                .define('G', Tags.Items.INGOTS_GOLD)
                .define('#', redstoneBlock)
                .unlockedBy("has_turtle", has(ModRegistry.Blocks.TURTLE_NORMAL.get()))
                .unlockedBy("has_advanced_turtle", has(ModRegistry.Blocks.TURTLE_ADVANCED.get()))
                .save(recipeOutput.withConditions(not(or(
                                modLoaded(ModIdConstants.ID_THERMAL),
                                modLoaded(ModIdConstants.ID_MEKANISM),
                                modLoaded(ModIdConstants.ID_POWAH)))),
                        FastLoc.modLoc("tundercharge_dynamo"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.THUNDERCHARGE_DYNAMO_BLOCK.get())
                .pattern("XRX")
                .pattern("X#X")
                .pattern("XGX")
                .define('X', Tags.Items.INGOTS_IRON)
                .define('G', Tags.Items.INGOTS_GOLD)
                .define('R', rfCoil)
                .define('#', machineFrame)
                .unlockedBy("has_turtle", has(ModRegistry.Blocks.TURTLE_NORMAL.get()))
                .unlockedBy("has_advanced_turtle", has(ModRegistry.Blocks.TURTLE_ADVANCED.get()))
                .save(recipeOutput.withConditions(and(
                                modLoaded(ModIdConstants.ID_THERMAL),
                                itemExists(ModIdConstants.ID_THERMAL, "machine_frame"),
                                itemExists(ModIdConstants.ID_THERMAL, "rf_coil"))),
                        FastLoc.modLoc("tundercharge_dynamo_thermal"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.THUNDERCHARGE_DYNAMO_BLOCK.get())
                .pattern("XRX")
                .pattern("X#X")
                .pattern("XGX")
                .define('X', Tags.Items.INGOTS_IRON)
                .define('G', Tags.Items.INGOTS_GOLD)
                .define('R', osmiumIngot)
                .define('#', steelCasing)
                .unlockedBy("has_turtle", has(ModRegistry.Blocks.TURTLE_NORMAL.get()))
                .unlockedBy("has_advanced_turtle", has(ModRegistry.Blocks.TURTLE_ADVANCED.get()))
                .save(recipeOutput.withConditions(and(
                                modLoaded(ModIdConstants.ID_MEKANISM),
                                itemExists(ModIdConstants.ID_MEKANISM, "steel_casing"),
                                itemExists(ModIdConstants.ID_MEKANISM, "ingot_osmium"))),
                        FastLoc.modLoc("tundercharge_dynamo_mekanism"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.THUNDERCHARGE_DYNAMO_BLOCK.get())
                .pattern("XRX")
                .pattern("X#X")
                .pattern("XGX")
                .define('X', Tags.Items.INGOTS_IRON)
                .define('G', Tags.Items.INGOTS_GOLD)
                .define('R', basicCapacitor)
                .define('#', dielectricCasing)
                .unlockedBy("has_turtle", has(ModRegistry.Blocks.TURTLE_NORMAL.get()))
                .unlockedBy("has_advanced_turtle", has(ModRegistry.Blocks.TURTLE_ADVANCED.get()))
                .save(recipeOutput.withConditions(and(
                                modLoaded(ModIdConstants.ID_POWAH),
                                itemExists(ModIdConstants.ID_POWAH, "dielectric_casing"),
                                itemExists(ModIdConstants.ID_POWAH, "capacitor_basic"))),
                        FastLoc.modLoc("tundercharge_dynamo_powah"));
    }

}
