package de.drache.customCrafting.Classes;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class CheckForCraftingRecipe {

    public static ItemStack checkRecipe(List<ItemStack> ingredients) {
        for (HashMap.Entry<List<ItemStack>, ItemStack> entry : CreateCraftingRecipe.stackHashMap.entrySet()) {
            List<ItemStack> recipe = entry.getKey();
            ItemStack result = entry.getValue();

            // Check if the ingredients match the recipe
            if (matchIngredients(recipe, ingredients)) {
                return result; // Return the resulting ItemStack if a match is found
            }
        }
        return null; // Return null if no matching recipe is found
    }

    private static boolean matchIngredients(List<ItemStack> recipe, List<ItemStack> ingredients) {
        // Check if sizes differ
        if (recipe.size() != ingredients.size()) {
            return false;
        }

        // Check ingredients in order according to the recipe
        for (int i = 0; i < recipe.size(); i++) {
            ItemStack recipeItem = recipe.get(i);
            ItemStack ingredientItem = ingredients.get(i);

            // If the recipe item is not null and does not match the ingredient, return false
            if (recipeItem != null && !recipeItem.isSimilar(ingredientItem)) {
                return false;
            }
        }
        return true; // All items match in order
    }

    public static boolean hasRecipeResult(ItemStack stack) {
        return CreateCraftingRecipe.stackHashMap.containsValue(stack);
    }
}
