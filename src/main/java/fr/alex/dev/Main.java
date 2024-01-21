package fr.alex.dev;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.getCommand("teleport").setExecutor(new teleport(this));

        getServer().getPluginManager().registerEvents(this, this);
    }

    // Commande setspawn
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setspawn") && sender instanceof Player) {
            Player player = (Player) sender;
            Location location = player.getLocation();
            World world = player.getWorld();
            getConfig().set("spawn.world", world.getName());
            getConfig().set("spawn.x", location.getX());
            getConfig().set("spawn.y", location.getY());
            getConfig().set("spawn.z", location.getZ());
            saveConfig();
            player.sendMessage(ChatColor.GREEN + "Spawn défini avec succès");
            return true;
        } else if (cmd.getName().equalsIgnoreCase("spawn") && sender instanceof Player) {
            Player player = (Player) sender;
            World world = Bukkit.getWorld(getConfig().getString("spawn.world"));
            double x = getConfig().getDouble("spawn.x");
            double y = getConfig().getDouble("spawn.y");
            double z = getConfig().getDouble("spawn.z");
            Location spawnLocation = new Location(world, x, y, z);
            player.teleport(spawnLocation);
            player.sendMessage(ChatColor.GREEN + "Vous avez été téléporté au spawn !");
        }
        return false;
    }

}
