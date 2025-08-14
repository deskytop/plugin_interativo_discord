package com.desky.deathwarn.discord

import com.desky.deathwarn.util.ImageConfig
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.Color
import java.time.Instant
import org.bukkit.entity.Player

object DiscordBot {
    private var jda: JDA? = null
    private var channel: TextChannel? = null

    fun start(token: String, channelId: String) {
        jda?.shutdown()
        jda = JDABuilder.createDefault(token)
            .addEventListeners(object : ListenerAdapter() {
                override fun onReady(event: ReadyEvent) {
                    channel = event.jda.getTextChannelById(channelId)
                    if (channel == null) {
                        println("❌ Erro: Canal do Discord não encontrado com ID: $channelId")
                    } else {
                        channel?.sendMessage("✅ Bot do Minecraft online!")?.queue()
                    }
                }

            })
            .build()
    }

    fun sendMessage(msg: String) {
        channel?.sendMessage(msg)?.queue()
    }

    fun sendDeathEmbed(player: Player, causeText: String) {
        val thumbnailUrl = if (LuckPermsUtil.isPlayerInPlanetaGroup(player)) {
            ImageConfig.getImageForPlayer(player.name)
        } else {
            ImageConfig.getFallbackImage()
        }
        val formattedMessage = formatDeathMessage(causeText)
        val embed = EmbedBuilder()
            .setTitle("💀 Jogador morreu!")
            .setDescription(formattedMessage)
            .setThumbnail(thumbnailUrl)
            .setColor(Color(0xDE3E53))
            .setFooter("CelestialSMP", null)
            .setTimestamp(Instant.now())
            .build()

        channel?.sendMessageEmbeds(embed)?.queue()
    }

        private fun formatDeathMessage(rawMessage: String): String {
            return "***$rawMessage***"
        }
    fun shutdown() {
        jda?.shutdown()
        jda = null
        channel = null
    }


}





