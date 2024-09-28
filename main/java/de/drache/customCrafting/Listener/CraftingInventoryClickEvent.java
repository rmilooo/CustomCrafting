package de.drache.customCrafting.Listener;

import de.drache.customCrafting.Classes.CheckForCraftingRecipe;
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

public class CraftingInventoryClickEvent implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        List<Integer> glassPaneSlots = IntStream.of(
                10, 11, 12,
                19, 20, 21,
                28, 29, 30
        ).boxed().toList();
        if (event.getWhoClicked() instanceof Player player) {
            Inventory playerInventory = CreateCraftingGui.inventoryHashMap.get(player);
            if (playerInventory != null && event.getInventory().equals(playerInventory)) {
                if (event.getCurrentItem() != null) {
                    if (event.getCurrentItem().isSimilar(CreateCraftingGui.createGlassPane(Material.RED_STAINED_GLASS_PANE)) || event.getCurrentItem().isSimilar(CreateCraftingGui.createGlassPane(Material.GRAY_STAINED_GLASS_PANE))) {
                        event.setCancelled(true);
                    }else {
                        if (player.getOpenInventory().getTopInventory().equals(playerInventory)){
                            if (event.getSlot() == 24){
                                ItemStack stack = event.getCurrentItem();
                                if (CheckForCraftingRecipe.hasRecipeResult(stack)){
                                    for (Integer i : glassPaneSlots){
                                        playerInventory.setItem(i, new ItemStack(Material.AIR, 1));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void startInventoryCheck(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                    Inventory playerInventory = CreateCraftingGui.inventoryHashMap.get(player);
                    if (playerInventory != null && player.getOpenInventory().getTopInventory().equals(playerInventory)) {
                        // Handle any necessary logic while the menu is open
                        List<Integer> glassPaneSlots = IntStream.of(
                                10, 11, 12,
                                19, 20, 21,
                                28, 29, 30
                        ).boxed().toList();

                        // Update crafting result
                        List<ItemStack> items = new ArrayList<>();
                        for (Integer i : glassPaneSlots) {
                            ItemStack item = playerInventory.getItem(i);
                            items.add(item != null ? item : new ItemStack(Material.BARRIER, 1));
                        }

                        // Check for crafting recipe and update the result
                        ItemStack craftingResult = CheckForCraftingRecipe.checkRecipe(items);
                        if (craftingResult != null) {
                            playerInventory.setItem(24, craftingResult);
                        } else {
                            playerInventory.setItem(24, CreateCraftingGui.createGlassPane(Material.RED_STAINED_GLASS_PANE));
                        }
                    } else {
                        // If the inventory is closed, cancel the task
                        this.cancel();
                    }
                }
            }.

            runTaskTimer(main.instance, 0,2); // Run every 2 ticks
        }
    }
