package com.mikitellurium.turtlecharginstation.event;

import com.mikitellurium.turtlecharginstation.TurtleChargingStationMod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LightningBolt;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TurtleChargingStationMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    @SubscribeEvent
    public static void onLightningStrike(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide) {
            return;
        }
        if (event.getEntity() instanceof LightningBolt lightningBolt) {
            event.getLevel().getServer().getPlayerList().broadcastSystemMessage(
                    Component.literal("Lightning strike at: " + lightningBolt.getOnPos()), false);
        }
    }


}
