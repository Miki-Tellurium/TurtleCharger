package com.mikitellurium.turtlecharginstation.datagen;

import com.mikitellurium.turtlecharginstation.TurtleChargingStationMod;
import com.mikitellurium.turtlecharginstation.registry.ModBlocks;
import com.mikitellurium.turtlecharginstation.util.ModIdConstants;
import dan200.computercraft.shared.ModRegistry;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator.getPackOutput());
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {

        ConditionalRecipe.builder()
                .addCondition(not(modLoaded(ModIdConstants.ID_THERMAL)))
                .addCondition(not(modLoaded(ModIdConstants.ID_MEKANISM)))
                .addCondition(not(modLoaded(ModIdConstants.ID_POWAH)))
                .addRecipe(
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TURTLE_CHARGING_STATION_BLOCK.get())
                .pattern("cgc")
                .pattern("gRg")
                .pattern("cIc")
                .define('c', Blocks.BLACK_CONCRETE)
                .define('g', Tags.Items.INGOTS_GOLD)
                .define('R', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .define('I', Tags.Items.STORAGE_BLOCKS_IRON)
                .group(TurtleChargingStationMod.MOD_ID)
                .unlockedBy("turtlecharging", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ModRegistry.Blocks.TURTLE_NORMAL.get(), ModRegistry.Blocks.TURTLE_ADVANCED.get()))::save)
                .build(consumer, new ResourceLocation(TurtleChargingStationMod.MOD_ID, "turtle_charging_station"));

        ConditionalRecipe.builder()
                .addCondition(modLoaded(ModIdConstants.ID_THERMAL))
                .addCondition(itemExists(ModIdConstants.ID_THERMAL, "energy_cell_frame"))
                .addRecipe(
                        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TURTLE_CHARGING_STATION_BLOCK.get())
                                .pattern("cgc")
                                .pattern("gFg")
                                .pattern("cIc")
                                .define('c', Blocks.BLACK_CONCRETE)
                                .define('g', Tags.Items.INGOTS_GOLD)
                                .define('F', ForgeRegistries.ITEMS.getValue(new ResourceLocation(ModIdConstants.ID_THERMAL, "energy_cell_frame")))
                                .define('I', Tags.Items.STORAGE_BLOCKS_IRON)
                                .group(TurtleChargingStationMod.MOD_ID)
                                .unlockedBy("turtlecharging", InventoryChangeTrigger.TriggerInstance.hasItems(
                                        ModRegistry.Blocks.TURTLE_NORMAL.get(), ModRegistry.Blocks.TURTLE_ADVANCED.get()))::save)
                .build(consumer, new ResourceLocation(TurtleChargingStationMod.MOD_ID, "turtle_charging_station_thermal"));


        ConditionalRecipe.builder()
                .addCondition(modLoaded(ModIdConstants.ID_MEKANISM))
                .addCondition(itemExists(ModIdConstants.ID_MEKANISM, "steel_casing"))
                .addRecipe(
                        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TURTLE_CHARGING_STATION_BLOCK.get())
                                .pattern("cgc")
                                .pattern("gSg")
                                .pattern("cIc")
                                .define('c', Blocks.BLACK_CONCRETE)
                                .define('g', Tags.Items.INGOTS_GOLD)
                                .define('S', ForgeRegistries.ITEMS.getValue(new ResourceLocation(ModIdConstants.ID_MEKANISM, "steel_casing")))
                                .define('I', Tags.Items.STORAGE_BLOCKS_IRON)
                                .group(TurtleChargingStationMod.MOD_ID)
                                .unlockedBy("turtlecharging", InventoryChangeTrigger.TriggerInstance.hasItems(
                                        ModRegistry.Blocks.TURTLE_NORMAL.get(), ModRegistry.Blocks.TURTLE_ADVANCED.get()))::save)
                .build(consumer, new ResourceLocation(TurtleChargingStationMod.MOD_ID, "turtle_charging_station_mekanism"));

        ConditionalRecipe.builder()
                .addCondition(modLoaded(ModIdConstants.ID_POWAH))
                .addCondition(itemExists(ModIdConstants.ID_POWAH, "dielectric_casing"))
                .addRecipe(
                        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TURTLE_CHARGING_STATION_BLOCK.get())
                                .pattern("cgc")
                                .pattern("gDg")
                                .pattern("cIc")
                                .define('c', Blocks.BLACK_CONCRETE)
                                .define('g', Tags.Items.INGOTS_GOLD)
                                .define('D', ForgeRegistries.ITEMS.getValue(new ResourceLocation(ModIdConstants.ID_POWAH, "dielectric_casing")))
                                .define('I', Tags.Items.STORAGE_BLOCKS_IRON)
                                .group(TurtleChargingStationMod.MOD_ID)
                                .unlockedBy("turtlecharging", InventoryChangeTrigger.TriggerInstance.hasItems(
                                        ModRegistry.Blocks.TURTLE_NORMAL.get(), ModRegistry.Blocks.TURTLE_ADVANCED.get()))::save
                ).build(consumer, new ResourceLocation(TurtleChargingStationMod.MOD_ID, "turtle_charging_station_powah"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.THUNDERCHARGE_DYNAMO_BLOCK.get())
                .pattern("XRX")
                .pattern("X#X")
                .pattern("XGX")
                .define('X', Tags.Items.INGOTS_IRON)
                .define('#', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .define('R', Blocks.LIGHTNING_ROD)
                .define('G', Tags.Items.INGOTS_GOLD)
                .group(TurtleChargingStationMod.MOD_ID)
                .unlockedBy("turtlecharging", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ModRegistry.Blocks.TURTLE_NORMAL.get(), ModRegistry.Blocks.TURTLE_ADVANCED.get()))
                .save(consumer);

    }

}
