package com.desky.deathwarn;

import com.desky.deathwarn.discord.DiscordBot;
import com.desky.deathwarn.util.Config;
import com.desky.deathwarn.util.ImageConfig;
import com.desky.deathwarn.listener.PlayerDeathListener;
import org.bukkit.plugin.java.JavaPlugin;
import com.desky.deathwarn.commands.DeathWarnCommand;

import java.util.Objects;

public class DeathWarn extends JavaPlugin {
    @Override
    public void onEnable() {
        Config.setup(this);

        ImageConfig.load(this);
        Objects.requireNonNull(getCommand("deathwarn")).setExecutor(new DeathWarnCommand(this));

        String token = Config.getDiscordToken();
        String channelId = Config.getDiscordChannelId();

        if (token.isEmpty() || channelId.isEmpty()) {
            getLogger().severe("❌ Plugin desativado: Token ou Channel ID ausente.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        logBanner();
        DiscordBot.INSTANCE.start(token, channelId);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        DiscordBot.INSTANCE.sendMessage("🟢 Plugin DeathWarn iniciado no servidor!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin desligado.");
    }

    private void logBanner() {
        getLogger().info("===============================================================================");
        getLogger().info("  _____                   _     _        __          __                       ");
        getLogger().info(" |  __ \\                 | |   | |       \\ \\        / /                       ");
        getLogger().info(" | |  | |   ___    __ _  | |_  | |__      \\ \\  /\\  / /    __ _   _ __   _ __  ");
        getLogger().info(" | |  | |  / _ \\  / _` | | __| | '_ \\      \\ \\/  \\/ /    / _` | | '__| | '_ \\ ");
        getLogger().info(" | |__| | |  __/ | (_| | | |_  | | | |      \\  /\\  /    | (_| | | |    | | | |");
        getLogger().info(" |_____/   \\___|  \\__,_|  \\__| |_| |_|       \\/  \\/      \\__,_| |_|    |_| |_|");
        getLogger().info("                                                                              ");
        getLogger().info("DeathWarn carregado com sucesso!");
        getLogger().info("===============================================================================");
    }
}