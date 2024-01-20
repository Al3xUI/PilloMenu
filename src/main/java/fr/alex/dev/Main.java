package fr.alex.dev;

import net.minecraft.server.v1_8_R3.ItemMapEmpty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class Main extends JavaPlugin implements Listener {
    private final Inventory inv;

    public Main(){
        inv = Bukkit.createInventory(null, 9, "Exemple");
        initializeItems();
    }

    public void initializeItems(){
        inv.addItem(CreateGuiItem(Material.GOLD_BLOCK, "Teleportation 1", "Description"));
        inv.addItem(CreateGuiItem(Material.DIAMOND_BLOCK, "Teleportation 2", "description"));
    }

    protected ItemStack CreateGuiItem(final Material material, final String Name, final String Lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(Name);
        item.setItemMeta((ItemMeta) Arrays.asList(Lore));
        return item;
    }

    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }
    @EventHandler
    public void openInventory(final InventoryClickEvent e) {
        if (!e.getInventory().equals(inv)) return;

        e.setCancelled(true);

        final ItemStack clieckedItem = e.getCurrentItem();

        if (clieckedItem== null || clieckedItem.getType().isBlock()) return;

        final Player p = (Player) e.getWhoClicked();

        p.sendMessage("You clicked");
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e){
        if (e.getInventory().equals(inv)){
            e.setCancelled(true);
        }
    }
}