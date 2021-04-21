package lee.code.onestopshop.commands.subcommands;

import lee.code.onestopshop.commands.SubCommand;
import lee.code.onestopshop.files.defaults.Config;
import lee.code.onestopshop.files.defaults.Lang;
import lee.code.onestopshop.OneStopShop;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reload extends SubCommand {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return Lang.MESSAGE_HELP_SUB_COMMAND_RELOAD.getConfigValue(null);
    }

    @Override
    public String getSyntax() {
        return "/shop reload";
    }

    @Override
    public String getPermission() {
        return "oss.shop.reload";
    }

    @Override
    public void perform(Player player, String[] args) {
        OneStopShop plugin = OneStopShop.getPlugin();

        plugin.reloadFiles();
        plugin.loadDefaults();
        plugin.getData().loadData();
        player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_RELOAD.getConfigValue(null));
        plugin.getPU().playXSound(player, Config.SOUND_TRANSACTION_SUCCESSFUL.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_TRANSACTION_SUCCESSFUL.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_TRANSACTION_SUCCESSFUL.getConfigValue(null)));
    }

    @Override
    public void performConsole(CommandSender console, String[] args) {
        OneStopShop plugin = OneStopShop.getPlugin();

        plugin.reloadFiles();
        plugin.loadDefaults();
        plugin.getData().loadData();
        console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_RELOAD.getConfigValue(null));
    }
}
