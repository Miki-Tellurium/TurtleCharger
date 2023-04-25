package com.mikitellurium.turtlecharginstation.item;

import com.mikitellurium.turtlecharginstation.TurtleChargingStationMod;
import com.mikitellurium.turtlecharginstation.block.ModBlocks;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TurtleChargingStationMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeTab {

    public static CreativeModeTab TAB_TURTLECHARGINGSTATION;

    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
        TAB_TURTLECHARGINGSTATION = event.registerCreativeModeTab(
                new ResourceLocation(TurtleChargingStationMod.MOD_ID, "turtlechargingstation_creative_tab"),
                builder -> builder.icon(() -> new ItemStack(ModBlocks.TURTLE_CHARGING_STATION_BLOCK.get()))
                        .title(Component.translatable("creativemodetab.turtlechargingstation_creative_tab")));
    }

}
