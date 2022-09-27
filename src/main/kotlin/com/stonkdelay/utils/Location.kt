package com.stonkdelay.utils

import com.stonkdelay.StonkDelay
import net.minecraft.scoreboard.Scoreboard
import net.minecraft.util.StringUtils.stripControlCodes
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import net.minecraftforge.fml.common.network.FMLNetworkEvent

object Location {
    var onHypixel = false
    var inSkyblock = false
    var ticks = 0


    @SubscribeEvent
    fun onConnect(event: FMLNetworkEvent.ClientConnectedToServerEvent) {
        onHypixel = StonkDelay.mc.runCatching {
            !event.isLocal && ((thePlayer?.clientBrand?.lowercase()?.contains("hypixel")
                ?: currentServerData?.serverIP?.lowercase()?.contains("hypixel")) == true)
        }.getOrDefault(false)
    }

    private fun cleanSB(scoreboard: String?): String? {
        return scoreboard?.let {
            stripControlCodes(scoreboard)
                .toCharArray()
                .filter { it.code in 21..126 }
                .joinToString("")
        }

    }

    @SubscribeEvent
    fun onTick(event: TickEvent) {
        if (event.phase != TickEvent.Phase.START) return
        if (onHypixel) {
            if (ticks % 10 == 0) {
                val title = cleanSB(StonkDelay.mc.theWorld?.scoreboard?.getObjectiveInDisplaySlot(1)?.displayName)
                inSkyblock = title?.contains("SKYBLOCK") == true
            }
        } else {
            inSkyblock = false
        }
        ticks++

    }

}