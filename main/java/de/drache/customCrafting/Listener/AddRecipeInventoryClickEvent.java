package de.drache.customCrafting.Listener;

import de.drache.customCrafting.Classes.CheckForCraftingRecipe;
import de.drache.customCrafting.Gui.CreateAddRecipeGui;
import de.drache.customCrafting.Gui.CreateCraftingGui;
import de.drache.customCrafting.main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class AddRecipeInventoryClickEvent implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        List<Integer> glassPaneSlots = IntStream.of(
                10, 11, 12,
                19, 20, 21,
                28, 29, 30
        ).boxed().toList();
        if (event.getWhoClicked() instanceof Player player) {
            Inventory playerInventory = CreateAddRecipeGui.inventoryHashMap.get(player);
            if (playerInventory != null && event.getInventory().equals(playerInventory)) {
                if (event.getCurrentItem() != null) {
                    if (event.getCurrentItem().isSimilar(CreateAddRecipeGui.createGlassPane(Material.RED_STAINED_GLASS_PANE)) || event.getCurrentItem().isSimilar(CreateCraftingGui.createGlassPane(Material.GRAY_STAINED_GLASS_PANE))) {
                        event.setCancelled(true);
                    }

                }
            }
        }
    }
}
