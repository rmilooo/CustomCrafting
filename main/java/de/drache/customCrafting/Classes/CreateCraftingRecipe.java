package de.drache.customCrafting.Classes;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CreateCraftingRecipe {
    public static HashMap<List<ItemStack>, ItemStack> stackHashMap = new HashMap<>();

    public static void createRecipe(CraftingTypes types, ItemStack stack, ItemStack keyItem, ItemStack... keyItem2) {
        String[] shape = types.getShape();
        List<ItemStack> recipe = new ArrayList<>();

        for (String row : shape) {
            ItemStack[] itemRow = new ItemStack[row.length()];
            for (int i = 0; i < row.length(); i++) {
                char symbol = row.charAt(i);
                if (symbol == '#') {
                    itemRow[i] = keyItem; // Replace # with keyItem
                } else if (symbol == '*') {
                    // Ensure we use keyItem2 based on the position, cycling through if necessary
                    itemRow[i] = keyItem2[i % keyItem2.length];
                } else if (symbol == '-') {
                    itemRow[i] = new ItemStack(Material.BARRIER); // Replace - with barrier
                } else {
                    itemRow[i] = null; // Empty slot
                }
            }
            // Convert itemRow to a List and add it to the recipe
            // Add each item to the recipe
            recipe.addAll(Arrays.asList(itemRow));
        }

        // Store the crafted recipe in the hash map
        stackHashMap.put(recipe, stack);
    }
}
