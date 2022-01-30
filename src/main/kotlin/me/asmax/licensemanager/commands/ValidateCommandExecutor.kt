package me.asmax.licensemanager.commands

import me.asmax.licensemanager.LicenseManager
import me.asmax.licensemanager.utils.Config
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ValidateCommandExecutor: CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            return true
        }
        var player: Player = sender

        if (!player.hasPermission("License.validate")) {
            player.sendMessage(LicenseManager.instance.unknown)
            return true
        }

        if (args.isNotEmpty()) {
            player.sendMessage("${LicenseManager.instance.prefix} Please use: /validate")
            return true
        }

        var valid = Config.getConfig().getBoolean("License.Verified")

        if (valid) {
            player.sendMessage("${LicenseManager.instance.prefix} §aYour License is §2§ovalid§a! §6You have the Pro version.")
            return true
        }
        player.sendMessage("${LicenseManager.instance.prefix} §cYour License is §4§oinvalid§c! §6You have the Lite version.")

        return true
    }
}