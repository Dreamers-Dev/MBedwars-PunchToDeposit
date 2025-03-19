package dev.dreamers.ptd.commands

import dev.dreamers.ptd.PunchToDeposit
import dev.dreamers.ptd.helpers.MessageHelper
import dev.dreamers.ptd.services.ConfigService
import dev.dreamers.ptd.services.MessageService
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class PTDCommand : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>,
    ): Boolean {
        if (args.isEmpty()) {
            MessageHelper.sendMessage(
                sender,
                MessageHelper.colorize(
                    "&7[&bPTD&7] &b${PunchToDeposit.PLUGIN_NAME} &7version &b${PunchToDeposit.PLUGIN_VERSION} &7by &bRafi &7(A.K.A Cipher)"
                ),
            )
            return true
        }

        if (args[0].equals("reload", true) || args[0].equals("rl", true)) {
            if (!sender.hasPermission("ptd.commands.reload")) {
                MessageHelper.sendMessage(
                    sender,
                    MessageService.INSUFFICIENT_PERMISSIONS.replace(
                        "%permission",
                        "ptd.commands.reload",
                    ),
                )
                return true
            }
            try {
                ConfigService.reload()
                MessageService.reload()
                MessageHelper.sendMessage(sender, MessageService.RELOAD_SUCCESS)
            } catch (e: Exception) {
                MessageHelper.sendMessage(sender, MessageService.RELOAD_FAILED)
                e.printStackTrace()
            }
        }
        return true
    }
}
