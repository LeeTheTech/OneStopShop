package lee.code.onestopshop.commands;

import lee.code.onestopshop.commands.subcommands.*;
import lee.code.onestopshop.files.defaults.Config;
import lee.code.onestopshop.files.defaults.Lang;
import lee.code.onestopshop.menusystem.menus.MainMenu;
import lee.code.onestopshop.OneStopShop;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lombok.Getter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor {
    @Getter public final ArrayList<SubCommand> subCommands = new ArrayList<>();

    public CommandManager() {
        subCommands.add(new Open());
        subCommands.add(new Reload());
        subCommands.add(new RemoveItem());
        subCommands.add(new AddItem());
        subCommands.add(new ItemInfo());
        subCommands.add(new Sell());
        subCommands.add(new SellAll());
        subCommands.add(new SellWand());
        subCommands.add(new Worth());
        subCommands.add(new Spawner());
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        OneStopShop plugin = OneStopShop.getPlugin();

        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (args.length > 0) {
                for (int i = 0; i < getSubCommands().size(); i++) {
                    if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())){
                        //perm check for sub command
                        if (p.hasPermission(getSubCommands().get(i).getPermission())) getSubCommands().get(i).perform(p, args);
                        else p.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_NO_PERMISSION.getConfigValue(null));
                        return true;
                    }
                }

                //plugin info
                int number = 1;
                List<String> lines = new ArrayList<>();
                lines.add(Lang.MESSAGE_HELP_DIVIDER.getConfigValue(null));
                lines.add(Lang.MESSAGE_HELP_TITLE.getConfigValue(null));
                lines.add("&r");

                //adds /shop
                lines.add(Lang.MESSAGE_HELP_SHOP_COMMAND.getConfigValue(new String[]{ String.valueOf(number) }));
                number++;

                for (int i = 0; i < getSubCommands().size(); i++) {
                    //perm check
                    if (p.hasPermission(getSubCommands().get(i).getPermission())) {
                        //add command to list
                        lines.add(Lang.MESSAGE_HELP_SUB_COMMAND.getConfigValue(new String [] { String.valueOf(number), getSubCommands().get(i).getSyntax(), getSubCommands().get(i).getDescription() }));
                        number++;
                    }
                }
                lines.add("&r");
                lines.add(Lang.MESSAGE_HELP_DIVIDER.getConfigValue(null));

                for (String line : lines) p.sendMessage(plugin.getPU().format(line));

            } else {
                plugin.getData().getPlayerMenuUtil(p.getUniqueId()).setCurrentMenu(plugin.getData().getMainMenu());
                new MainMenu(plugin.getData().getPlayerMenuUtil(p.getUniqueId())).openMenu(plugin.getData().getMainMenu());
                plugin.getPU().playXSound(p, Config.SOUND_MENU_OPEN.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_MENU_OPEN.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_MENU_OPEN.getConfigValue(null)));
            }
            return true;
        }
        //console command
        if (args.length > 0) {
            for (int i = 0; i < getSubCommands().size(); i++) {
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                    getSubCommands().get(i).performConsole(sender, args);
                    return true;
                }
            }

        }
        return true;
    }
}