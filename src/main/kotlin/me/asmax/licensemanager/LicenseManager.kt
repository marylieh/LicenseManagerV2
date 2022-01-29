package me.asmax.licensemanager

import me.asmax.licensemanager.commands.*
import me.asmax.licensemanager.license.LicenseManagement
import me.asmax.licensemanager.listener.JoinListener
import me.asmax.licensemanager.listener.PlayerChangeGamemodeListener
import me.asmax.licensemanager.mongodb.MongoManager
import me.asmax.licensemanager.utils.Config
import me.asmax.licensemanager.utils.GameStateManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class LicenseManager : JavaPlugin() {

    companion object {
        lateinit var instance: LicenseManager
        private set
    }

    val prefix = "§8● §9LicenseManager §8|§7"
    val unknown = "§fUnknown command. Type \\\"/help\\\" for help."

    override fun onLoad() {
        instance = this
        Config.Config()
    }

    override fun onEnable() {
        registerCommands()
        registerListener()

        MongoManager.connect("Verification")

        if (Config.getConfig().get("License.Key") != null) {

            if (Config.getConfig().getString("License.Key").equals("lite", true)) {
                GameStateManager.liteVersion = true
                GameStateManager.installedLite = true
                Config.getConfig().set("License.Verified", false)
                Config.save()
            } else {
                LicenseManagement.initLicenseCheck()
            }
        } else {
            Bukkit.getLogger().warning("$prefix No License Key found. Please set a License key in your Server Config. Lite version is activated.")
        }

        Bukkit.getLogger().info("$prefix §aLicenseManager has been enabled.")
    }

    override fun onDisable() {
        Config.save()

        Bukkit.getLogger().info("$prefix §aLicenseManager has been disabled.")
    }

    private fun registerCommands() {
        val activateLiteCommand = getCommand("activatelite") ?: error("Couldn't get info command! This should not happen!")
        val forwardCommand = getCommand("forward") ?: error("Couldn't get info command! This should not happen!")
        val installKeyCommand = getCommand("installkey") ?: error("Couldn't get info command! This should not happen!")
        val keyCommand = getCommand("key") ?: error("Couldn't get info command! This should not happen!")
        val serverNameCommand = getCommand("servername") ?: error("Couldn't get info command! This should not happen!")
        val setServerNameCommand = getCommand("setservername") ?: error("Couldn't get info command! This should not happen!")
        val toggleNotificationsCommand = getCommand("togglenotifications") ?: error("Couldn't get info command! This should not happen!")
        val validateCommand = getCommand("validate") ?: error("Couldn't get info command! This should not happen!")

        activateLiteCommand.setExecutor(ActivateLiteCommandExecutor())
        forwardCommand.setExecutor(ForwardCommandExecutor())
        installKeyCommand.setExecutor(InstallKeyCommandExecutor())
        keyCommand.setExecutor(KeyCommandExecutor())
        serverNameCommand.setExecutor(ServerNameCommandExecutor())
        setServerNameCommand.setExecutor(SetServerNameCommandExecutor())
        toggleNotificationsCommand.setExecutor(ToggleNotificationsCommandExecutor())
        validateCommand.setExecutor(ValidateCommandExecutor())
    }

    private fun registerListener() {
        val pluginManager = Bukkit.getPluginManager()

        pluginManager.registerEvents(JoinListener(), this)
        pluginManager.registerEvents(PlayerChangeGamemodeListener(), this)
    }

}