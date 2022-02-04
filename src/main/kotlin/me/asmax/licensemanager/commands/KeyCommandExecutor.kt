package me.asmax.licensemanager.commands

import me.asmax.licensemanager.LicenseManager
import me.asmax.licensemanager.license.LicenseManagement
import me.asmax.licensemanager.utils.Config
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class KeyCommandExecutor: CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            return true
        }
        val player: Player = sender

        if (!player.hasPermission("License.key.show")) {
            player.sendMessage(LicenseManager.instance.unknown)
            LicenseManagement.notifyOP("${LicenseManager.instance.prefix} The Player ${player.name} unsuccessfully used the command '/key'")
            return true
        }

        if (args.isNotEmpty()) {
            player.sendMessage("${LicenseManager.instance.prefix} Please use: /key")
            return true
        }

        val key = Config.getConfig().getString("License.Key")

        player.sendMessage("${LicenseManager.instance.prefix} Current installed License Key -> $key")
        return true
    }
}