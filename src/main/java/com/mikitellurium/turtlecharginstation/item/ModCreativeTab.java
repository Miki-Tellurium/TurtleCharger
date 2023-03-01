package com.mikitellurium.turtlecharginstation.item;

import com.mikitellurium.turtlecharginstation.block.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTab {

    public static final CreativeModeTab TAB_TURTLE_CHARGING_STATION = new CreativeModeTab("turtle_charging_station_creative_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.TURTLE_CHARGING_STATION_BLOCK.get());
        }
    };

}

