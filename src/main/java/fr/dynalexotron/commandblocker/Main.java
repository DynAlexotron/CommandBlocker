package fr.dynalexotron.commandblocker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        this.saveDefaultConfig();
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        List<String> disabledCommands = this.getConfig().getStringList("disabled-commands");
        if(disabledCommands.isEmpty()) return;
        String[] args = event.getMessage().split(" ");

        if(disabledCommands.stream().anyMatch(s -> s.equalsIgnoreCase(args[0]))) {
            if(event.getPlayer().isOp() && !this.getConfig().getBoolean("disabled-for-op")) return;
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "Erreur : " + ChatColor.WHITE + "Commande désactivée");
        }
    }
}
