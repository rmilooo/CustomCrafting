package de.drache.customCrafting.Commands;

import de.drache.customCrafting.Gui.CreateAddRecipeGui;
import de.drache.customCrafting.Gui.CreateCraftingGui;
import de.drache.customCrafting.Listener.CraftingInventoryClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddRecipeCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check if the command sender is a player
        if (sender instanceof Player player) {
            if (player.isOp() || player.hasPermission("cc.admin")) {

                // Open the crafting inventory for the player
                CreateAddRecipeGui.initializeInventory(player);
                player.openInventory(CreateAddRecipeGui.inventoryHashMap.get(player));
                return true;
            }else{
                player.sendMessage("§4You don't have permission to use this Command!");
            }
        }else {
            // Notify console that the command can only be executed by a player
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player.");
        }
        return false;
    }
}