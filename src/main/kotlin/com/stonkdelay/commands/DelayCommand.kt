package com.stonkdelay.commands

import com.stonkdelay.StonkDelay
import com.stonkdelay.features.Delay
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.util.ChatComponentText

class DelayCommand : CommandBase() {
    override fun getCommandName(): String {
        return "stonkdelay"
    }

    override fun getCommandAliases(): List<String> {
        return listOf(
            "sd"
        )
    }

    override fun getCommandUsage(sender: ICommandSender?): String {
        return "/$commandName <delay in milliseconds|reset>"
    }

    override fun getRequiredPermissionLevel(): Int {
        return 0
    }

    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        if (args.isEmpty()) {
            StonkDelay.config.settings.enabled = !StonkDelay.config.settings.enabled
            StonkDelay.config.save()
            sender.addChatMessage(ChatComponentText(if (StonkDelay.config.settings.enabled) {
                "§aStonkDelay enabled. (delay: §b${StonkDelay.config.settings.delay} §ams)"
            } else {
                "§cStonkDelay disabled."
            }))
        } else if (args.size == 1) {
            if (args[0] == "reset" || args[0] == "rs") {
                Delay.resetAll()
                sender.addChatMessage(ChatComponentText("§aMade all blocks reappear"))
                return
            }
            try {
                val delay = args[0].toInt()
                StonkDelay.config.settings.delay = delay
                StonkDelay.config.save()
                sender.addChatMessage(ChatComponentText("§aDelay set to $delay ms."))
            } catch (e: NumberFormatException) {
                sender.addChatMessage(ChatComponentText("§cNot a valid delay!"))
            }
        }
    }
}