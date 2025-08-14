package com.desky.deathwarn.commands

import com.desky.deathwarn.DeathWarn
import com.desky.deathwarn.discord.DiscordBot
import com.desky.deathwarn.util.Config
import com.desky.deathwarn.util.ImageConfig
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class DeathWarnCommand(private val plugin: DeathWarn) : CommandExecutor {

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (!sender.hasPermission("deathwarn.reload")) {
            sender.sendMessage("§cVocê não tem permissão para isso.")
            return true
        }

        if (args.size != 1 || args[0].lowercase() != "reload") {
            sender.sendMessage("§cComando incompleto, tente: /deathwarn reload.")
            return true
        }

        plugin.logger.info("${sender.name} executou o comando deathwarn reload")

        try {
            Config.reload()
            ImageConfig.load(plugin)
            DiscordBot.shutdown()

            val token = Config.getDiscordToken()
            val channelId = Config.getDiscordChannelId()

            if (token.isBlank() || channelId.isBlank()) {
                sender.sendMessage("§cToken ou canal não configurado corretamente.")
                return true
            }

            DiscordBot.start(token, channelId)
            sender.sendMessage("§a[DeathWarn] Plugin recarregado com sucesso.")
        } catch (ex: Exception) {
            plugin.logger.severe("Erro ao recarregar o DeathWarn: ${ex.message}")
            sender.sendMessage("§cErro ao recarregar o plugin, veja o console para detalhes.")
        }

        return true
    }
}