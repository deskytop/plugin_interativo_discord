package com.desky.deathwarn.util

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

object ImageConfig {
    private lateinit var file: File
    private lateinit var config: FileConfiguration
    private val planetaImages = mutableMapOf<String, String>()
    private var fallbackImage: String = ""

    @JvmStatic
    fun load(plugin: JavaPlugin) {
        file = File(plugin.dataFolder, "imagens.yml")
        if (!file.exists()) plugin.saveResource("imagens.yml", false)
        config = YamlConfiguration.loadConfiguration(file)

        fallbackImage = config.getString("fallback") ?: ""

        planetaImages.clear()
        val planetaSection = config.getConfigurationSection("planeta")
        planetaSection?.getKeys(false)?.forEach { playerName ->
            planetaImages[playerName.lowercase()] = planetaSection.getString(playerName, "") ?: ""
        }

        plugin.logger.info("Imagens carregadas para grupo planeta: $planetaImages")
    }

    fun getImageForPlayer(playerName: String): String {
        return planetaImages[playerName.lowercase()] ?: fallbackImage
    }

    fun getFallbackImage(): String {
        return fallbackImage
    }
}