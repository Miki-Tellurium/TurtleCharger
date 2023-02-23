package com.mikitellurium.turtlecharger.item;

import com.mikitellurium.turtlecharger.block.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTab {

    public static final CreativeModeTab TAB_TURTLE_CHARGER = new CreativeModeTab("turtle_charger_creative_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.TURTLE_CHARGER_BLOCK.get());
        }
    };

}

