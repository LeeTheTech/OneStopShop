package lee.code.onestopshop.commands.subcommands;

import lee.code.onestopshop.OneStopShop;
import lee.code.onestopshop.commands.SubCommand;
import lee.code.onestopshop.files.defaults.Lang;
import lee.code.onestopshop.itembuilders.SpawnerBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Scanner;

public class Spawner extends SubCommand {

    @Override
    public String getName() {
        return "spawner";
    }

    @Override
    public String getDescription() {
        return Lang.MESSAGE_HELP_SUB_COMMAND_SPAWNER.getConfigValue(null);
    }

    @Override
    public String getSyntax() {
        return "/shop spawner &f<mob> <amount> <player>";
    }

    @Override
    public String getPermission() {
        return "oss.shop.spawner";
    }

    @Override
    public void perform(Player player, String[] args) {

        OneStopShop plugin = OneStopShop.getPlugin();

        //shop spawner
        if (args.length == 1) {
            player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_COMMAND_SPAWNER_ARG_1.getConfigValue(null));
            return;
        }

        if (args.length > 1) {

            int amount = 1;

            if (args.length > 2) {
                Scanner sellScanner = new Scanner(args[2]);
                if (sellScanner.hasNextInt()) {
                    amount = Integer.parseInt(args[2]);
                } else {
                    player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_COMMAND_SPAWNER_VALUE.getConfigValue(new String[]{args[2]}));
                    return;
                }
            }

            Player target = player;
            //check if player is online and if a target was given
            if (args.length >= 4) {

                if (plugin.getPU().getOnlinePlayers().contains(args[3])) {
                    //target player
                    target = Bukkit.getPlayer(args[3]);
                } else {
                    player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_PLAYER_NOT_ONLINE.getConfigValue(new String[]{args[3]}));
                    return;
                }
            }

            //mob check
            if (plugin.getData().getSpawnerMobs().contains(args[1].toUpperCase())) {

                EntityType mob = EntityType.valueOf(args[1].toUpperCase());
                ItemStack spawner = new SpawnerBuilder().setMob(mob).buildItemStack();
                spawner.setAmount(amount);

                //space check
                if (plugin.getPU().getAmountOfFreeSpace(target, spawner) > 0) {
                    target.getInventory().addItem(spawner);
                } else {
                    target.getLocation().getWorld().dropItemNaturally(target.getLocation(), spawner);
                }
                String itemString = plugin.getPU().formatMatFriendly(spawner);
                target.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_GIVE_SPAWNER_SUCCESSFUL.getConfigValue(new String[]{plugin.getPU().formatAmount(amount), itemString}));

                if (target != player) {
                    player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_GAVE_SPAWNER_SUCCESSFUL.getConfigValue(new String[]{target.getDisplayName(), plugin.getPU().formatAmount(amount), itemString}));
                }
            } else {
                player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_COMMAND_SPAWNER_DOES_NOT_EXIST.getConfigValue(new String[]{args[1].toUpperCase()}));
            }
        }
    }


    @Override
    public void performConsole(CommandSender console, String[] args) {

        OneStopShop plugin = OneStopShop.getPlugin();

        //shop spawner
        if (args.length == 1) {
            console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_COMMAND_SPAWNER_ARG_1.getConfigValue(null));
            return;
        }

        if (args.length > 1) {

            int amount = 1;

            if (args.length > 2) {
                Scanner sellScanner = new Scanner(args[2]);
                if (sellScanner.hasNextInt()) {
                    amount = Integer.parseInt(args[2]);
                } else {
                    console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_COMMAND_SPAWNER_VALUE.getConfigValue(new String[]{args[2]}));
                    return;
                }
            }

            Player target;
            //check if player is online and if a target was given
            if (args.length >= 4) {

                if (plugin.getPU().getOnlinePlayers().contains(args[3])) {
                    //target player
                    target = Bukkit.getPlayer(args[3]);
                } else {
                    console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_PLAYER_NOT_ONLINE.getConfigValue(new String[]{args[3]}));
                    return;
                }
            } else {
                console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_NO_TARGET_PLAYER.getConfigValue(null));
                return;
            }

            //mob check
            if (plugin.getData().getSpawnerMobs().contains(args[1].toUpperCase())) {

                EntityType mob = EntityType.valueOf(args[1].toUpperCase());
                ItemStack spawner = new SpawnerBuilder().setMob(mob).buildItemStack();
                spawner.setAmount(amount);

                //space check
                if (plugin.getPU().getAmountOfFreeSpace(target, spawner) > 0) {
                    target.getInventory().addItem(spawner);
                } else {
                    target.getLocation().getWorld().dropItemNaturally(target.getLocation(), spawner);
                }
                String itemString = plugin.getPU().formatMatFriendly(spawner);
                target.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_GIVE_SPAWNER_SUCCESSFUL.getConfigValue(new String[]{plugin.getPU().formatAmount(amount), itemString}));

                console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_GAVE_SPAWNER_SUCCESSFUL.getConfigValue(new String[]{target.getDisplayName(), plugin.getPU().formatAmount(amount), itemString}));
            } else {
                console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_COMMAND_SPAWNER_DOES_NOT_EXIST.getConfigValue(new String[]{args[1].toUpperCase()}));
            }
        }
    }
}

