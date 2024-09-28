package de.drache.customCrafting.Classes;


public class CraftingRecipes {
    public static void init(){
        CreateCraftingRecipe.stackHashMap.putAll(FileManagement.recipeMap);
    }
}
