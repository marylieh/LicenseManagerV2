package me.asmax.licensemanager.utils

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException

object Config {

    private lateinit var file: File
    private lateinit var config: YamlConfiguration

    fun config() {
        val dir = File("./plugins/LicenseManager")

        if (!dir.exists()) {
            dir.mkdirs()
        }

        file = File(dir, "config.yml")

        if (!file.exists()) {
            try {
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        config = YamlConfiguration.loadConfiguration(file)
    }

    fun getConfig(): YamlConfiguration {
        return config
    }

    fun save() {
        try {
            config.save(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}