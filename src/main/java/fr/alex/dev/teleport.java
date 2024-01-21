package fr.alex.dev;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class teleport implements Listener, CommandExecutor {

    private final Main plugin;
    private static final String INVENTORY_NAME = "PilloMenu";
    private static final Material TELEPORT_ITEM_MATERIAL = Material.REDSTONE_BLOCK;

    public teleport(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getCommand("teleport").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Seuls les joueurs peuvent utiliser cette commande !");
            return true;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("teleport")) {
            if (player.isOp()) {
                openTeleportGUI(player);
            } else {
                player.sendMessage("Vous n'avez pas la permission.");
            }
            return true;
        }
        return false;
    }

    public static void openTeleportGUI(Player player) {
        Inventory teleportGUI = Bukkit.createInventory(null, 27, INVENTORY_NAME);
        ItemStack serverItem = new ItemStack(TELEPORT_ITEM_MATERIAL);
        ItemMeta serverMeta = serverItem.getItemMeta();
        serverMeta.setDisplayName("Teleportation");
        serverItem.setItemMeta(serverMeta);
        teleportGUI.setItem(13, serverItem);

        player.openInventory(teleportGUI);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getClickedInventory().getName().equals(INVENTORY_NAME)) {
            if (event.getCurrentItem() != null && event.getCurrentItem().getType() == TELEPORT_ITEM_MATERIAL) {
                event.setCancelled(true);
                Player player = (Player) event.getWhoClicked();
                player.performCommand("spawn");
                player.closeInventory();
            }
        }
    }
}