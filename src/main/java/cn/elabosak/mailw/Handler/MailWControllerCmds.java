package cn.elabosak.mailw.Handler;

import cn.elabosak.mailw.API.MailWAPI;
import cn.elabosak.mailw.Main;
import cn.elabosak.mailw.Utils.removeEmailAccount;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

/**
 * @author ElaBosak
 */
public class MailWControllerCmds implements CommandExecutor{

    Main plugin;

    @Override
    public boolean onCommand (CommandSender sender, Command cmd, String lable, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("MailW.admin")) {
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
        if(args[0].equalsIgnoreCase("remove")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("MailW.admin")) {
                    if (args.length == 1) {
                        p.sendMessage(ChatColor.RED+"§lPlease Type A Correct Target To Continue");
                        return true;
                    } else {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target.getUniqueId() != null && new removeEmailAccount().remove(target.getUniqueId())) {
                            p.sendMessage(ChatColor.GREEN+"§lEmail Removed Successful");
                            return true;
                        }else {
                            p.sendMessage(ChatColor.RED+"§lEmail Removed Failed");
                            return true;
                        }
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
        if (args[0].equalsIgnoreCase("test")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("MailW.admin")) {
                    if (args.length == 1) {
                        p.sendMessage(ChatColor.RED+"§lPlease Type A Correct Target To Continue");
                        return true;
                    } else {
//                        Player target = Bukkit.getPlayer(args[1]);
                        if (MailWAPI.sendEmail(p, "MailW", "Test form MailW", "Nice Work, That's fine!")) {
                            p.sendMessage(ChatColor.GREEN+"§lThe operator sent a test email to your mailbox, please check it~");
                            return true;
                        } else {
                            p.sendMessage(ChatColor.RED+"§lTest Email Sent Failed");
                            return true;
                        }
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
}
