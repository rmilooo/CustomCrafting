package de.drache.customCrafting;

import de.drache.customCrafting.Classes.FileManagement;
import de.drache.customCrafting.Commands.AddRecipeCommand;
import de.drache.customCrafting.Commands.CraftCommand;
import de.drache.customCrafting.Listener.AddRecipeInventoryClickEvent;
import de.drache.customCrafting.Listener.CraftingInventoryClickEvent;
import de.drache.customCrafting.Listener.OnInventoryClose;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public final class main extends JavaPlugin {
    public static main instance;
    public static List<Integer> glassPaneSlots = IntStream.of(
            10, 11, 12,
            19, 20, 21,
            28, 29, 30
    ).boxed().toList();
    @Override
    public void onEnable() {
        instance = this;
        this.register();
        FileManagement.getInstance().init();
    }

    @Override
    public void onDisable() {
        FileManagement.getInstance().saveFile();
    }
    public void log(String message) {
        Bukkit.getLogger().info(message);
    }
    public void register(){
        Bukkit.getPluginManager().registerEvents(new CraftingInventoryClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new OnInventoryClose(), this);
        Bukkit.getPluginManager().registerEvents(new AddRecipeInventoryClickEvent(), this);
        Objects.requireNonNull(this.getCommand("addRecipe")).setExecutor(new AddRecipeCommand());
        Objects.requireNonNull(this.getCommand("craft")).setExecutor(new CraftCommand());
    }
}
