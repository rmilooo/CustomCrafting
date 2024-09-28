package de.drache.customCrafting.Commands;

import de.drache.customCrafting.Classes.CraftingRecipes;
import de.drache.customCrafting.Listener.CraftingInventoryClickEvent;
import de.drache.customCrafting.Gui.CreateCraftingGui;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CraftCommand implements CommandExecutor {



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check if the command sender is a player
        if (sender instanceof Player) {
            Player player = (Player) sender;

            CraftingRecipes.init();
            CreateCraftingGui.initializeInventory(player);
            player.openInventory(CreateCraftingGui.inventoryHashMap.get(player));
            CraftingInventoryClickEvent.startInventoryCheck(player);
            return true;
        } else {
            // Notify console that the command can only be executed by a player
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player.");
            return false;
        }
    }
}
