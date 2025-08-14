package com.desky.deathwarn.listener

import com.desky.deathwarn.discord.DiscordBot
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class PlayerDeathListener : Listener {
    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val player = event.entity
        val deathMessage = event.deathMessage ?: "Morte desconhecida"


        DiscordBot.sendDeathEmbed(player, deathMessage)
    }
}