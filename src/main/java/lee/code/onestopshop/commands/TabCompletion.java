package lee.code.onestopshop.commands;

import lee.code.onestopshop.OneStopShop;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;
import java.util.Collections;

public class TabCompletion implements TabCompleter {

    private final List<String> subCommands = Arrays.asList("add", "remove", "reload", "iteminfo", "sell", "sellall", "worth", "open", "spawner", "sellwand");
    private final List<String> blank = new ArrayList<>();

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String alias, @Nonnull String[] args) {
        OneStopShop plugin = OneStopShop.getPlugin();

        if (sender instanceof Player) {
            if (args.length == 1) {
                List<String> hasCommand = new ArrayList<>();
                for (String pluginCommand : subCommands) if (sender.hasPermission("oss.shop." + pluginCommand)) hasCommand.add(pluginCommand);
                return StringUtil.copyPartialMatches(args[0], hasCommand, new ArrayList<>());
            } else if (args[0].equals("add")) {
                if (args.length == 2) return StringUtil.copyPartialMatches(args[1], plugin.getData().getShopList(), new ArrayList<>());
                else if (args.length == 3) return StringUtil.copyPartialMatches(args[2], Collections.singletonList("<sell>"), new ArrayList<>());
                else if (args.length == 4) return StringUtil.copyPartialMatches(args[3], Collections.singletonList("<buy>"), new ArrayList<>());
            } else if (args[0].equals("open")) {
                if (args.length == 2) return StringUtil.copyPartialMatches(args[1], plugin.getData().getMenuList(), new ArrayList<>());
                if (args.length == 3) return StringUtil.copyPartialMatches(args[2], plugin.getData().getDataMenuUtil(args[1]).getMenuShops(), new ArrayList<>());
            } else if (args[0].equals("remove") && args.length == 2) {
                return StringUtil.copyPartialMatches(args[1], plugin.getData().getShopList(), new ArrayList<>());
            } else if (args[0].equals("spawner")) {
                if (args.length == 2) return StringUtil.copyPartialMatches(args[1], plugin.getData().getSpawnerMobs(), new ArrayList<>());
                else if (args.length == 3) return StringUtil.copyPartialMatches(args[2], Collections.singletonList("<amount>"), new ArrayList<>());
                else if (args.length == 4) return StringUtil.copyPartialMatches(args[3], plugin.getPU().getOnlinePlayers(), new ArrayList<>());
            } else if (args[0].equals("sellwand")) {
                if (args.length == 2) return StringUtil.copyPartialMatches(args[1], Collections.singletonList("<amount>"), new ArrayList<>());
                else if (args.length == 3) return StringUtil.copyPartialMatches(args[2], plugin.getPU().getOnlinePlayers(), new ArrayList<>());
            } else return blank;
        }
        return blank;
    }
}
