package me.asmax.licensemanager.listener

import me.asmax.licensemanager.utils.Config
import me.asmax.licensemanager.utils.GameStateManager
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class JoinListener: Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        var player: Player = event.player

        if (Config.getConfig().getBoolean("License.Verified")) {
            GameStateManager.liteVersion = false
            return
        }

        GameStateManager.liteVersion = true
        Bukkit.getServer().defaultGameMode = GameMode.ADVENTURE
        GameStateManager.liteVersion = false
        player.gameMode = GameMode.ADVENTURE
        GameStateManager.liteVersion = true
    }
}