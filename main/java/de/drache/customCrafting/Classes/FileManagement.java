package de.drache.customCrafting.Classes;

import de.drache.customCrafting.main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FileManagement {
    private File dataFile;
    private YamlConfiguration dataConfig;
    public static FileManagement instance;
    public static Map<List<ItemStack>, ItemStack> recipeMap = Collections.synchronizedMap(new LinkedHashMap<>());


    public FileManagement() {
        instance = this;
    }

    public static FileManagement getInstance() {
        if (instance == null) {
            instance = new FileManagement();
        }
        return instance;
    }

    public void init() {
        File dataFolder = Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("CustomCrafting")).getDataFolder();
        if (!dataFolder.exists() && dataFolder.mkdirs()) {
            main.instance.log("Directory created successfully");
        }

        dataFile = new File(dataFolder, "recipes.yml");
        if (!dataFile.exists()) {
            try {
                if (dataFile.createNewFile()) {
                    main.instance.log("File created successfully");
                } else {
                    main.instance.log("File already exists");
                }
            } catch (IOException e) {
                main.instance.log("Error creating file: " + e.getMessage());
            }
        }
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        loadFile();
    }

    public void saveFile() {
        saveHashMap();
        if (dataFile == null || dataConfig == null) {
            main.instance.getLogger().warning("Save failed: dataFile or dataConfig is null");
            return;
        }
        try {
            dataConfig.save(dataFile);
            main.instance.log("Recipes saved successfully");
        } catch (IOException e) {
            main.instance.getLogger().warning("Error saving file: " + e.getMessage());
        }
    }

    private void saveHashMap() {
        if (recipeMap == null || recipeMap.isEmpty()) {
            main.instance.getLogger().warning("No recipes to save.");
            return;
        }

        int i = 0;
        for (Map.Entry<List<ItemStack>, ItemStack> entry : recipeMap.entrySet()) {
            List<ItemStack> ingredients = entry.getKey();
            ItemStack result = entry.getValue();

            if (ingredients == null || result == null) {
                main.instance.getLogger().warning("Null value encountered in recipeMap at entry #" + (i + 1));
                continue; // Skip null entries
            }

            i++;
            dataConfig.set("recipe" + i + ".ingredients", ingredients);
            dataConfig.set("recipe" + i + ".result", result);
        }
        dataConfig.set("existingRecipes", i);
    }

    public void loadFile() {
        if (dataConfig == null) {
            main.instance.getLogger().warning("loadFile failed: dataConfig is null");
            return;
        }

        int recipeCount = dataConfig.getInt("existingRecipes", 0);
        if (recipeCount <= 0) {
            main.instance.log("No recipes found to load.");
            return;
        }

        for (int i = 1; i <= recipeCount; i++) {
            List<ItemStack> ingredients = (List<ItemStack>) dataConfig.get("recipe" + i + ".ingredients");
            ItemStack result = dataConfig.getItemStack("recipe" + i + ".result");

            if (ingredients == null || result == null) {
                main.instance.getLogger().warning("Failed to load recipe #" + i + ": ingredients or result is null.");
                continue; // Skip invalid entries
            }

            recipeMap.put(ingredients, result);
        }

        main.instance.log(recipeCount + " recipes loaded successfully");
    }
}
