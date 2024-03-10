package com.mikitellurium.turtlechargingstation.registry;

import com.mikitellurium.turtlechargingstation.TurtleChargingStationMod;
import com.mikitellurium.turtlechargingstation.registry.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModCreativeTab {

    private static final ItemGroup TAB_TURTLECHARGINGSTATION  = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.TURTLE_CHARGING_STATION_BLOCK.asItem()))
            .displayName(Text.translatable("creativemodetab.turtlechargingstation_creative_tab"))
            .entries((context, entries) -> {
                entries.add(ModBlocks.TURTLE_CHARGING_STATION_BLOCK);
                entries.add(ModBlocks.THUNDERCHARGE_DYNAMO_BLOCK);
            })
            .build();

    public static void register() {
        Registry.register(Registries.ITEM_GROUP, new Identifier(TurtleChargingStationMod.MOD_ID, "creative_tab"),
                TAB_TURTLECHARGINGSTATION );
    }

}
