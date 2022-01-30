package me.asmax.licensemanager.listener

import me.asmax.licensemanager.LicenseManager
import me.asmax.licensemanager.utils.GameStateManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerGameModeChangeEvent

class PlayerChangeGamemodeListener: Listener {

    @EventHandler
    fun onGamemodeChange(event: PlayerGameModeChangeEvent) {
        if (GameStateManager.liteVersion) {
            event.isCancelled = true
            event.player.sendMessage("${LicenseManager.instance.prefix} You are not be able to change your gamemode in the lite version.")
        }
    }
}