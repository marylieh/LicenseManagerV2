package me.asmax.licensemanager.license

import me.asmax.licensemanager.LicenseManager
import me.asmax.licensemanager.mongodb.MongoManager
import me.asmax.licensemanager.utils.Config
import me.asmax.licensemanager.utils.GameStateManager
import org.bson.Document
import org.bukkit.Bukkit
import java.util.*

object LicenseManagement {

    fun initLicenseCheck() {

        if (GameStateManager.forwarding) {
            GameStateManager.lastCheck = true
            GameStateManager.firstCheck = false
            GameStateManager.errorLevel = 0
            return
        }

        if (!checkLicense()) {
            GameStateManager.lastCheck = false
            notifyOP("${LicenseManager.instance.prefix} Cannot sent heartbeat to verification server")
        } else {
            GameStateManager.lastCheck = true
            GameStateManager.errorLevel = 0
            Bukkit.getScheduler().cancelTasks(LicenseManager.instance)
        }

        if (!GameStateManager.lastCheck) {
            GameStateManager.errorLevel++
        }

        when (GameStateManager.errorLevel) {
            1 -> notifyOP("${LicenseManager.instance.prefix} If the heartbeat is not successfully in the next 20 tries, the server will restart and" +
                    "the lite version will be activated: Try ${GameStateManager.errorLevel}/20")
            2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19 -> notifyOP("${LicenseManager.instance.prefix} Reconnecting to verification server in 20 tries if the connection can't be established" +
                    ", the server will restart and the lite version will be activated: Try ${GameStateManager.errorLevel}/20")
            20 -> {
                notifyOP("${LicenseManager.instance.prefix} License management tool is waiting 300 seconds before initializing the lite version!")
                initServerShutdown()
            }
        }

        if (GameStateManager.firstCheck) {
            GameStateManager.firstCheck = false
            notifyOP("${LicenseManager.instance.prefix} The connection to the verification server has been established and the connection is stable")
            if (GameStateManager.errorLevel < 1) {
                notifyOP("${LicenseManager.instance.prefix} First heartbeat has been sent successfully to verification server")
            }
        }

        if (GameStateManager.newKey) {
            if (GameStateManager.errorLevel < 1) {
                GameStateManager.firstCheck = false
                notifyOP("${LicenseManager.instance.prefix} License key has been verified: is valid")
                notifyOP("${LicenseManager.instance.prefix} New key has been installed. You have to do one final step, to use all plugins in the pro version: restart server")
            }
        }

        if (GameStateManager.lastCheck) {
            Config.getConfig().set("License.Verified", true)
        } else {
            Config.getConfig().set("License.Verified", false)
        }

        sendHeartbeatContinuously()
    }

    fun checkLicense(): Boolean {
        var key = Config.getConfig().getString("License.Key")
        var serverName = serverName()
        var searchKey = Document("Key", key)
        var document = MongoManager.getDocument(searchKey)

        if (document == null) {
            notifyOP("${LicenseManager.instance.prefix} Invalid License Key -> Check your key on asmax.me/key")
            return false
        }

        if (document.getString("Key").equals(key, false)) {
            return Objects.equals(document.getString("ServerName"), serverName)
        }
        return false
    }

    private fun serverName(): String? {
        if (Config.getConfig().get("License.ServerName") == null) {
            Config.getConfig().set("License.ServerName", "ServerName")
            return "null"
        }

        return Config.getConfig().getString("License.ServerName")
    }

    private fun sendHeartbeatContinuously() {
        Bukkit.getScheduler().runTaskLater(LicenseManager.instance, Runnable() {
            @Override
            fun run() {
                initLicenseCheck()
            }
        }, 100)
    }

    fun notifyOP(msg: String) {
        if (!GameStateManager.notificationsToggled) {
            return
        }

        Bukkit.getOnlinePlayers().forEach {
            if (it.isOp) {
                it.sendMessage(msg)
            }
        }
    }

    private fun initServerShutdown() {
        Bukkit.getScheduler().runTaskLater(LicenseManager.instance, Runnable() {
            @Override
            fun run() {
                Bukkit.getServer().shutdown()
            }
        }, 6000)
    }
}