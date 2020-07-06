package cn.elabosak.mailw.handler;

import cn.elabosak.mailw.utils.SetEmailAccount;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ElaBosak
 */
public class MailWCmds implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("MailW.general")) {
                    p.sendMessage(ChatColor.RED+"§lPlease Type SubCommand");
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED+"§lYou Don't Have Permission");
                    return true;
                }
            } else {
                sender.sendMessage(ChatColor.RED+"§lYou Must Do This As A Player");
                return true;
            }
        }
        if (args[0].equalsIgnoreCase("set")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("MailW.general")) {
                    if (args.length == 2) {
                        if (new SetEmailAccount().set(p.getUniqueId(), args[1])){
                            p.sendTitle(ChatColor.GREEN+"§lEmail Set Successfully", ChatColor.BLUE+"§lPowered By = MailW =", 40,20, 20);
                            return true;
                        }else {
                            p.sendMessage(ChatColor.RED+"§lEmail Set Failed");
                            return true;
                        }
                    } else {
                        p.sendMessage(ChatColor.RED+"§lPlease Type Your Email");
                        return true;
                    }
                } else {
                    sender.sendMessage(ChatColor.RED+"§lYou Don't Have Permission");
                    return true;
                }
            } else {
                sender.sendMessage(ChatColor.RED+"§lYou Must Do This As A Player");
                return true;
            }
        }
        if (args[0].equalsIgnoreCase("remove")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("MailW.general")) {
                    if (new SetEmailAccount().remove(p.getUniqueId())){
                        p.sendTitle(ChatColor.GOLD+"§lEmail Removed Successfully", ChatColor.BLUE+"§lPowered By = MailW =", 40,20, 20);
                        return true;
                    }else {
                        p.sendMessage(ChatColor.RED+"§lEmail Removed Failed");
                        return true;
                    }
                } else {
                    sender.sendMessage(ChatColor.RED+"§lYou Don't Have Permission");
                    return true;
                }
            } else {
                sender.sendMessage(ChatColor.RED+"§lYou Must Do This As A Player");
                return true;
            }
        }
        //ToDo 更多命令
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> subCommands = new ArrayList<>();
            subCommands.add("set");
            subCommands.add("remove");
            subCommands.add("help");
            return subCommands;
        }
        if (args[0].equalsIgnoreCase("set")) {
            List<String> example = new ArrayList<>();
            example.add("example@example.com");
            return example;
        }
        return null;
    }
}
