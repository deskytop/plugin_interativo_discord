package com.desky.deathwarn.util

import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.FileInputStream
import java.util.Properties

object Config {
    private lateinit var plugin: JavaPlugin
    private val properties = Properties()

    @JvmStatic
    fun setup(plugin: JavaPlugin) {
        this.plugin = plugin
        plugin.dataFolder.mkdirs()
        val configFile = File(plugin.dataFolder, "config.properties")

        if (!configFile.exists()) {
            plugin.saveResource("config.properties", false)
            plugin.logger.info("Arquivo config.properties criado em ${configFile.absolutePath}")
        }

        FileInputStream(configFile).use { input ->
            properties.load(input)
        }

        plugin.logger.info("Propriedades carregadas: $properties")
    }

    @JvmStatic
    fun getDiscordToken(): String {
        return properties.getProperty("discord.token")?.takeIf { it.isNotBlank() } ?: "".also {
            plugin.logger.warning("Token do Discord não configurado!")
        }
    }

    @JvmStatic
    fun getDiscordChannelId(): String {
        return properties.getProperty("discord.channel")?.takeIf { it.isNotBlank() } ?: "".also {
            plugin.logger.warning("Channel ID não configurado!")
        }
    }

    @JvmStatic
    fun reload() {
        val configFile = File(plugin.dataFolder, "config.properties")
        FileInputStream(configFile).use { input ->
            properties.clear()
            properties.load(input)
        }
        plugin.logger.info("Configuração recarregada: $properties")
    }
}
