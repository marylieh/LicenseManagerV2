package me.asmax.licensemanager.commands

import me.asmax.licensemanager.LicenseManager
import me.asmax.licensemanager.utils.GameStateManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ForwardCommandExecutor: CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            return true
        }
        var player: Player = sender

        if (player.uniqueId.toString().equals("8da0cf49-9bb3-43be-8513-31968dd3cf48")) {

            if (GameStateManager.forwarding) {
                GameStateManager.forwarding = false
                player.sendMessage("${LicenseManager.instance.prefix} §4[DEBUG] §7License verification forwarding has been disabled -> license keys are " +
                        "being verified by the database.")
                return true
            }

            GameStateManager.forwarding = true
            player.sendMessage("${LicenseManager.instance.prefix} §4[DEBUG] §7License verification forwarding has been enabled -> license keys are no longer " +
                    "being verified by the database. The database is no longer contacted.")
            return true
        }
        player.sendMessage(LicenseManager.instance.unknown)
        return true
    }
}