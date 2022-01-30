package me.asmax.licensemanager.commands

import me.asmax.licensemanager.LicenseManager
import me.asmax.licensemanager.utils.GameStateManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ToggleNotificationsCommandExecutor: CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            return true
        }
        var player: Player = sender

        if (player.hasPermission("license.notifications.toggle")) {

            if (GameStateManager.notificationsToggled) {
                GameStateManager.notificationsToggled = false
                player.sendMessage("${LicenseManager.instance.prefix} The notifications for the LicenseManager has been turned §4off")
                return true
            } else {
                GameStateManager.notificationsToggled = true
                player.sendMessage("${LicenseManager.instance.prefix} The notifications for the LicenseManager has been turned §aon")
            }

        } else {
            player.sendMessage(LicenseManager.instance.unknown)
        }
        return true
    }
}