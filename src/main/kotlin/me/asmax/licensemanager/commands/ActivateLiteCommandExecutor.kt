package me.asmax.licensemanager.commands

import me.asmax.licensemanager.LicenseManager
import me.asmax.licensemanager.utils.Config
import me.asmax.licensemanager.utils.GameStateManager
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ActivateLiteCommandExecutor: CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            return true
        }
        val player: Player = sender

        if (!player.hasPermission("License.lite.activate")) {
            player.sendMessage(LicenseManager.instance.unknown)
            return true
        }

        if (args.isNotEmpty()) {
            player.sendMessage("${LicenseManager.instance.prefix} Please use: /activatelite")
            return true
        }

        Config.getConfig().set("License.Key", "lite")
        Config.getConfig().set("License.Verified", false)
        Config.save()
        GameStateManager.liteVersion = true
        GameStateManager.installedLite = true

        player.sendMessage("${LicenseManager.instance.prefix} Lite version will be activated...")
        Bukkit.getServer().reload()
        return true
    }
}