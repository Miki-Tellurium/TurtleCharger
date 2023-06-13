package com.mikitellurium.turtlecharginstation.item;

import com.mikitellurium.turtlecharginstation.TurtleChargingStationMod;
import com.mikitellurium.turtlecharginstation.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = TurtleChargingStationMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(
            Registries.CREATIVE_MODE_TAB, TurtleChargingStationMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TAB_TURTLECHARGINGSTATION = CREATIVE_TABS.register(
            "creative_tab",
            () -> CreativeModeTab.builder()
            .title(Component.translatable("creativemodetab.turtlechargingstation_creative_tab"))
            .icon(() -> new ItemStack(ModBlocks.TURTLE_CHARGING_STATION_BLOCK.get()))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_TABS.register(eventBus);
    }

}
