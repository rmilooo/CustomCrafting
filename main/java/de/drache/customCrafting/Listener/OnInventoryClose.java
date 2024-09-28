package de.drache.customCrafting.Listener;

import de.drache.customCrafting.Classes.CraftingRecipes;
import de.drache.customCrafting.Classes.FileManagement;
import de.drache.customCrafting.Gui.CreateAddRecipeGui;
import de.drache.customCrafting.main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class OnInventoryClose implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        // Check if the player has a registered inventory in the custom GUI
        Inventory inv = CreateAddRecipeGui.inventoryHashMap.get(player);
        if (inv != null && event.getInventory().equals(inv)) {

            // List to store the recipe ingredients
            List<ItemStack> stackList = new ArrayList<>();

            // Check if the result slot (24) is empty
            ItemStack resultItem = inv.getItem(24);
            if (resultItem == null || resultItem.getType() == Material.AIR) {
                player.sendMessage("§cRecipe creation failed: No result item found. Please place an item in the result slot.");
                return;
            }

            boolean recipeComplete = true;
            boolean allEmpty = true;

            // Collect ingredients from the glassPaneSlots and ensure they are valid
            for (Integer i : main.glassPaneSlots) {
                ItemStack stack = inv.getItem(i);
                if (stack == null || stack.getType() == Material.AIR) {
                    stackList.add(new ItemStack(Material.BARRIER));  // Placeholder for missing ingredients
                    recipeComplete = false;
                } else {
                    stackList.add(stack);
                    allEmpty = false;
                }
            }

            // Additional feedback based on whether the recipe is complete or not
            if (allEmpty) {
                player.sendMessage("§cRecipe creation failed: No ingredients provided.");
            } else if (!recipeComplete) {
                player.sendMessage("§ePartial recipe found: Missing ingredients have been replaced with barriers.");
            } else {
                player.sendMessage("§aRecipe complete! Saving your recipe...");
            }

            // Add the recipe to the recipe map
            FileManagement.recipeMap.put(stackList, resultItem);
            CraftingRecipes.init();
            player.sendMessage("§aRecipe has been saved successfully!");

            // Optional: Log the saved recipe to the console for debugging
            main.instance.log("Saved recipe with ingredients: " + stackList + " and result: " + resultItem);
        }
    }
}
