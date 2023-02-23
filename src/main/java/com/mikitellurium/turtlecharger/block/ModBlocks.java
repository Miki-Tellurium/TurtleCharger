package com.mikitellurium.turtlecharger.block;

import com.mikitellurium.turtlecharger.TurtleChargerMod;
import com.mikitellurium.turtlecharger.block.custom.TurtleChargerBlock;
import com.mikitellurium.turtlecharger.energy.ModEnergyStorage;
import com.mikitellurium.turtlecharger.item.ModCreativeTab;
import com.mikitellurium.turtlecharger.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, TurtleChargerMod.MOD_ID);

    public static final RegistryObject<Block> TURTLE_CHARGER_BLOCK = registerBlock("turtle_charger_block", () ->
            new TurtleChargerBlock(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).instabreak()),
            ModCreativeTab.TAB_TURTLE_CHARGER);

    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    public static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                           CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
