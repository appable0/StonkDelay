package com.stonkdelay

import com.stonkdelay.commands.DelayCommand
import com.stonkdelay.config.Config
import com.stonkdelay.features.Delay
import com.stonkdelay.utils.Location
import net.minecraft.client.Minecraft
import net.minecraft.init.Blocks
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import java.io.File

@Mod(
    modid = StonkDelay.MOD_ID,
    name = StonkDelay.MOD_NAME,
    version = StonkDelay.MOD_VERSION,
    clientSideOnly = true
)
class StonkDelay {

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        val directory = File(event.modConfigurationDirectory, "stonkDelay")
        directory.mkdirs()
        config = Config(File(directory, "settings.json"))
        config.load()
    }

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        ClientCommandHandler.instance.registerCommand(DelayCommand())
        listOf(
            Delay,
            Location
        ).forEach(MinecraftForge.EVENT_BUS::register)
    }



    companion object {
        val mc: Minecraft = Minecraft.getMinecraft()
        lateinit var config: Config
        const val MOD_ID = "stonkdelay"
        const val MOD_NAME = "StonkDelay"
        const val MOD_VERSION = "0.1"
    }
}