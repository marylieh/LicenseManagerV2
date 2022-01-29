package me.asmax.licensemanager.commands

import me.asmax.licensemanager.LicenseManager
import me.asmax.licensemanager.license.LicenseManagement
import me.asmax.licensemanager.utils.Config
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetServerNameCommandExecutor: CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            return true
        }
        var player: Player = sender

        if (!player.hasPermission("License.name.change")) {
            player.sendMessage(LicenseManager.instance.unknown)
            LicenseManagement.notifyOP("${LicenseManager.instance.prefix} The Player ${player.name} unsuccessfully used the command '/setservername'")
            return true
        }

        if (args.size != 1) {
            player.sendMessage("${LicenseManager.instance.prefix} Please use: /setservername <ServerName>")
            return true
        }

        Config.getConfig().set("License.ServerName", args[0])
        Config.save()

        player.sendMessage("${LicenseManager.instance.prefix} ServerName Change detected. New ServerName -> ${args[0]}")
        return true
    }
}