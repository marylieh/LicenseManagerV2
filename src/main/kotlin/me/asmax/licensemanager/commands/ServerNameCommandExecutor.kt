package me.asmax.licensemanager.commands

import me.asmax.licensemanager.LicenseManager
import me.asmax.licensemanager.license.LicenseManagement
import me.asmax.licensemanager.utils.Config
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ServerNameCommandExecutor: CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            return true
        }
        var player: Player = sender

        if (!player.hasPermission("License.name.show")) {
            player.sendMessage(LicenseManager.instance.unknown)
            LicenseManagement.notifyOP("${LicenseManager.instance.prefix} The Player ${player.name} unsuccessfully used the command '/servername'")
            return true
        }

        if (args.isNotEmpty()) {
            player.sendMessage("${LicenseManager.instance.prefix} Please use: /servername")
            return true
        }

        var serverName = Config.getConfig().getString("License.ServerName")

        player.sendMessage("${LicenseManager.instance.prefix} Current installed ServerName -> $serverName")
        return true
    }
}