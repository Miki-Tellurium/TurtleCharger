package com.mikitellurium.turtlechargingstation.registry;

import com.mikitellurium.telluriumforge.registry.InitializedRegistry;
import com.mikitellurium.telluriumforge.registry.RegistryHelper;
import com.mikitellurium.turtlechargingstation.TurtleChargingStationMod;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModCreativeTab implements InitializedRegistry {

    private static ItemGroup TAB_TURTLECHARGINGSTATION;

    @Override
    public void init(RegistryHelper helper) {
        TAB_TURTLECHARGINGSTATION = helper.registerItemGroup("creative_tab",
                FabricItemGroup.builder()
                        .icon(() -> new ItemStack(ModBlocks.TURTLE_CHARGING_STATION_BLOCK.asItem()))
                        .displayName(Text.translatable("creativemodetab.turtlechargingstation_creative_tab"))
                        .entries((context, entries) -> {
                            entries.add(ModBlocks.TURTLE_CHARGING_STATION_BLOCK);
                            entries.add(ModBlocks.THUNDERCHARGE_DYNAMO_BLOCK);
                        })
                        .build());
    }

}
