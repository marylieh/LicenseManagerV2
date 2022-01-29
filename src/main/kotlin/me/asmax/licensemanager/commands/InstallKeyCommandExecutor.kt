package me.asmax.licensemanager.commands

import me.asmax.licensemanager.LicenseManager
import me.asmax.licensemanager.license.LicenseManagement
import me.asmax.licensemanager.utils.Config
import me.asmax.licensemanager.utils.GameStateManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class InstallKeyCommandExecutor: CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            return true
        }
        var player: Player = sender

        if (!player.hasPermission("License.key.install")) {
            player.sendMessage(LicenseManager.instance.unknown)
            LicenseManagement.notifyOP("${LicenseManager.instance.prefix} The Player ${player.name} unsuccessfully used the command '/installkey'")
            return true
        }

        if (args.size != 1) {
            player.sendMessage("${LicenseManager.instance.prefix} Please use: /installkey <Key>")
            return true
        }

        Config.getConfig().set("License.Key", args[0])
        Config.save()
        player.sendMessage("${LicenseManager.instance.prefix} Key Change detected. new License Key -> ${args[0]}")
        player.sendMessage("${LicenseManager.instance.prefix} New Key installed [${args[0]}] -> Waiting for verification from service...")

        GameStateManager.newKey = true

        if (GameStateManager.installedLite) {
            LicenseManagement.initLicenseCheck()
        }
        return true
    }
}