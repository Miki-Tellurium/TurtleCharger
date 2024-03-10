package com.mikitellurium.turtlechargingstation.datagen;

import com.mikitellurium.turtlechargingstation.registry.ModBlocks;
import dan200.computercraft.shared.ModRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricDataOutput generator) {
        super(generator);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.TURTLE_CHARGING_STATION_BLOCK)
                .pattern("cgc")
                .pattern("gRg")
                .pattern("cIc")
                .input('c', Blocks.BLACK_CONCRETE)
                .input('g', Items.GOLD_INGOT)
                .input('R', Items.REDSTONE_BLOCK)
                .input('I', Items.IRON_BLOCK)
                .criterion(FabricRecipeProvider.hasItem(ModRegistry.Items.TURTLE_NORMAL.get()),
                        FabricRecipeProvider.conditionsFromItem(ModRegistry.Items.TURTLE_NORMAL.get()))
                .criterion(FabricRecipeProvider.hasItem(ModRegistry.Items.TURTLE_ADVANCED.get()),
                        FabricRecipeProvider.conditionsFromItem(ModRegistry.Items.TURTLE_ADVANCED.get()))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.THUNDERCHARGE_DYNAMO_BLOCK)
                .pattern("XRX")
                .pattern("X#X")
                .pattern("XGX")
                .input('G', Items.GOLD_INGOT)
                .input('R', Blocks.LIGHTNING_ROD)
                .input('#', Items.REDSTONE_BLOCK)
                .input('X', Items.IRON_INGOT)
                .criterion(FabricRecipeProvider.hasItem(ModRegistry.Items.TURTLE_NORMAL.get()),
                        FabricRecipeProvider.conditionsFromItem(ModRegistry.Items.TURTLE_NORMAL.get()))
                .criterion(FabricRecipeProvider.hasItem(ModRegistry.Items.TURTLE_ADVANCED.get()),
                        FabricRecipeProvider.conditionsFromItem(ModRegistry.Items.TURTLE_ADVANCED.get()))
                .offerTo(exporter);
    }

    //todo: add conditional recipe
//    ConditionalRecipe.builder()
//                .addCondition(not(modLoaded(ModIntegrationUtil.ID_THERMAL)))
//                .addCondition(not(modLoaded(ModIntegrationUtil.ID_MEKANISM)))
//                .addCondition(not(modLoaded(ModIntegrationUtil.ID_POWAH)))
//                .addRecipe(
//                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TURTLE_CHARGING_STATION_BLOCK.get())
//                .pattern("cgc")
//                .pattern("gRg")
//                .pattern("cIc")
//                .define('c', Blocks.BLACK_CONCRETE)
//                .define('g', Tags.Items.INGOTS_GOLD)
//                .define('R', Tags.Items.STORAGE_BLOCKS_REDSTONE)
//                .define('I', Tags.Items.STORAGE_BLOCKS_IRON)
//                .group(TurtleChargingStationMod.MOD_ID)
//                .unlockedBy("turtlecharging", InventoryChangeTrigger.TriggerInstance.hasItems(
//                        ModRegistry.Blocks.TURTLE_NORMAL.get(), ModRegistry.Blocks.TURTLE_ADVANCED.get()))::save
//        ).build(consumer, new ResourceLocation(TurtleChargingStationMod.MOD_ID, "turtle_charging_station"));
//
//        ConditionalRecipe.builder()
//                .addCondition(modLoaded(ModIntegrationUtil.ID_THERMAL))
//                .addCondition(itemExists(ModIntegrationUtil.ID_THERMAL, "energy_cell_frame"))
//                .addRecipe(
//                        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TURTLE_CHARGING_STATION_BLOCK.get())
//                                .pattern("cgc")
//                                .pattern("gFg")
//                                .pattern("cIc")
//                                .define('c', Blocks.BLACK_CONCRETE)
//                                .define('g', Tags.Items.INGOTS_GOLD)
//                                .define('F', ForgeRegistries.ITEMS.getValue(new ResourceLocation(ModIntegrationUtil.ID_THERMAL, "energy_cell_frame")))
//                                .define('I', Tags.Items.STORAGE_BLOCKS_IRON)
//                                .group(TurtleChargingStationMod.MOD_ID)
//                                .unlockedBy("turtlecharging", InventoryChangeTrigger.TriggerInstance.hasItems(
//                                        ModRegistry.Blocks.TURTLE_NORMAL.get(), ModRegistry.Blocks.TURTLE_ADVANCED.get()))::save
//                ).build(consumer, new ResourceLocation(TurtleChargingStationMod.MOD_ID, "turtle_charging_station_thermal"));
//
//
//        ConditionalRecipe.builder()
//                .addCondition(modLoaded(ModIntegrationUtil.ID_MEKANISM))
//                .addCondition(itemExists(ModIntegrationUtil.ID_MEKANISM, "steel_casing"))
//                .addRecipe(
//                        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TURTLE_CHARGING_STATION_BLOCK.get())
//                                .pattern("cgc")
//                                .pattern("gSg")
//                                .pattern("cIc")
//                                .define('c', Blocks.BLACK_CONCRETE)
//                                .define('g', Tags.Items.INGOTS_GOLD)
//                                .define('S', ForgeRegistries.ITEMS.getValue(new ResourceLocation(ModIntegrationUtil.ID_MEKANISM, "steel_casing")))
//                                .define('I', Tags.Items.STORAGE_BLOCKS_IRON)
//                                .group(TurtleChargingStationMod.MOD_ID)
//                                .unlockedBy("turtlecharging", InventoryChangeTrigger.TriggerInstance.hasItems(
//                                        ModRegistry.Blocks.TURTLE_NORMAL.get(), ModRegistry.Blocks.TURTLE_ADVANCED.get()))::save
//                ).build(consumer, new ResourceLocation(TurtleChargingStationMod.MOD_ID, "turtle_charging_station_mekanism"));
//
//        ConditionalRecipe.builder()
//                .addCondition(modLoaded(ModIntegrationUtil.ID_POWAH))
//                .addCondition(itemExists(ModIntegrationUtil.ID_POWAH, "dielectric_casing"))
//                .addRecipe(
//                        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TURTLE_CHARGING_STATION_BLOCK.get())
//                                .pattern("cgc")
//                                .pattern("gDg")
//                                .pattern("cIc")
//                                .define('c', Blocks.BLACK_CONCRETE)
//                                .define('g', Tags.Items.INGOTS_GOLD)
//                                .define('D', ForgeRegistries.ITEMS.getValue(new ResourceLocation(ModIntegrationUtil.ID_POWAH, "dielectric_casing")))
//                                .define('I', Tags.Items.STORAGE_BLOCKS_IRON)
//                                .group(TurtleChargingStationMod.MOD_ID)
//                                .unlockedBy("turtlecharging", InventoryChangeTrigger.TriggerInstance.hasItems(
//                                        ModRegistry.Blocks.TURTLE_NORMAL.get(), ModRegistry.Blocks.TURTLE_ADVANCED.get()))::save
//                ).build(consumer, new ResourceLocation(TurtleChargingStationMod.MOD_ID, "turtle_charging_station_powah"));
//

}
