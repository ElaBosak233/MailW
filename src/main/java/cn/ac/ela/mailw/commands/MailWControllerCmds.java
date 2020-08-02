package cn.ac.ela.mailw.commands;

import cn.ac.ela.mailw.api.MailWApi;
import cn.ac.ela.mailw.sql.MailWSettings;
import cn.ac.ela.mailw.sql.PlayerEmail;
import cn.ac.ela.mailw.utils.Base64Run;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author ElaBosak
 */
public class MailWControllerCmds implements TabExecutor {

    JavaPlugin plugin;
    public MailWControllerCmds(JavaPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand (CommandSender sender, Command cmd, String lable, String[] args) {
        if (args.length == 0) {
            if (sender.hasPermission("MailW.admin")) {
                sender.sendMessage(ChatColor.RED+"§lPlease Type SubCommand");
                return true;
            } else {
                sender.sendMessage(ChatColor.RED+"§lYou Don't Have Permission");
                return true;
            }
        }
//        if(args[0].equalsIgnoreCase("remove")) {
//            if (sender instanceof ConsoleCommandSender) {
//                if (sender.hasPermission("MailW.admin")) {
//                    if (args.length == 1) {
//                        sender.sendMessage(ChatColor.RED+"§lPlease Type A Correct Target To Continue");
//                        return true;
//                    } else {
//                        Player target = Bukkit.getPlayer(args[1]);
//                        if (target.getUniqueId() != null && new SetEmailAccount().remove(target.getUniqueId())) {
//                            sender.sendMessage(ChatColor.GREEN+"§lEmail Removed Successful");
//                            return true;
//                        }else {
//                            sender.sendMessage(ChatColor.RED+"§lEmail Removed Failed");
//                            return true;
//                        }
//                    }
//                } else {
//                    sender.sendMessage(ChatColor.RED+"§lYou Don't Have Permission");
//                    return true;
//                }
//            } else {
//                sender.sendMessage(ChatColor.RED+"§lYou Must Do This As A Player");
//                return true;
//            }
//        }

        if (args[0].equalsIgnoreCase("set")) {
            if (sender instanceof ConsoleCommandSender) {
                if (sender.hasPermission("MailW.admin")) {
                    if (args.length == 5) {
                        try {
                            Connection con = MailWApi.getConnection();
                            String password = Base64Run.encoder(args[4]);
                            if (MailWSettings.selectEmail(con) != null || MailWSettings.selectSmtp(con) != null || MailWSettings.selectPort(con) != null || MailWSettings.selectPasswd(con) != null) {
                                MailWSettings.updateTable(con);
                            }
                            if (MailWSettings.insert(con, args[1], args[2], args[3], password)) {
                                sender.sendMessage(ChatColor.GREEN+"§lMailW Set Successfully");
                                return true;
                            } else {
                                sender.sendMessage(ChatColor.RED+"§lMailW Set Failed");
                                return true;
                            }
                        } catch (SQLException e) {
                            sender.sendMessage(ChatColor.RED+"§lMailW Set Failed");
                            return true;
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED+"§lPlease Type The Correct Targets! You Need Type "+ChatColor.AQUA+"/MailWController <Email> <Smtp> <Port> <Passwd>");
                        return true;
                    }
                }
            } else {
                sender.sendMessage(ChatColor.RED+"§lOnly The Terminal Can Execute This Command");
                return true;
            }
        }
//        if (args[0].equalsIgnoreCase("test")) {
//            if (sender.hasPermission("MailW.admin")) {
//                try {
//                    Connection con = MailWApi.getConnection();
//                    if (MailWSettings.selectEmail(con) == null || MailWSettings.selectSmtp(con) == null || MailWSettings.selectPort(con) == null || MailWSettings.selectPasswd(con) == null) {
//                        sender.sendMessage(ChatColor.BLUE+"= Please Init The MailW By Using"+ChatColor.GOLD+" /MailWController set <Email> <Smtp> <Port> <Passwd> "+ChatColor.BLUE+"=");
//                        con.close();
//                        return true;
//                    } else if (args.length == 1) {
//                        sender.sendMessage(ChatColor.RED+"§lPlease Type A Correct Target To Continue");
//                        return true;
//                    } else {
//                        Player target = Bukkit.getPlayer(args[1]);
//                        try {
//                            if (MailWApi.sendEmail(target, "MailW", "Hi! This is = MailW =", "<h1>Hello "+target.getName()+",</h1><p> This is = MailW =, If you receive this email, it means that you have bound successfully. The sending of this email is directly controlled by the administrator, not automatically. It is only used by the administrator for self-test.<p>")) {
//                                target.sendMessage(ChatColor.GREEN+"§lThe operator sent a test email to your mailbox, please check it~");
//                                return true;
//                            } else {
//                                sender.sendMessage(ChatColor.RED+"§lTest Email Sent Failed, Player Does NOT Exist OR Player Is NOT Set = MailW =");
//                                return true;
//                            }
//                        } catch (Exception e) {
//                            sender.sendMessage(ChatColor.RED+"§lTest Email Sent Failed, Player Does NOT Exist OR Player Is NOT Set = MailW =");
//                            return true;
//                        }
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                    return true;
//                }
//            } else {
//                sender.sendMessage(ChatColor.RED+"§lYou Don't Have Permission");
//                return true;
//            }
//        }
        if (args[0].equalsIgnoreCase("send")) {
            if (sender.hasPermission("MailW.admin")) {
                try {
                    Connection con = MailWApi.getConnection();
                    if (MailWSettings.selectEmail(con) == null || MailWSettings.selectSmtp(con) == null || MailWSettings.selectPort(con) == null || MailWSettings.selectPasswd(con) == null) {
                        sender.sendMessage(ChatColor.BLUE+"= Please Init The MailW By Using"+ChatColor.GOLD+" /MailWController set <Email> <Smtp> <Port> <Password> "+ChatColor.BLUE+"=");
                        con.close();
                        return true;
                    } else if (args.length < 3) {
                        sender.sendMessage(ChatColor.RED+"§lPlease Type The Correct Targets To Continue "+ChatColor.GOLD+" /MailWController send <Player> <Template> ");
                        return true;
                    } else {
                        File file = new File("plugins/MailW/template/"+args[2]+"/index.html");
                        if (file.exists()) {
                            try {
                                String read = MailWApi.readHtml(file);
                                if (!Objects.equals(read, "err")) {
                                    String sd = MailWApi.htmlGetSender(read);
                                    String tt = MailWApi.htmlGetTitle(read);
                                    String content = MailWApi.htmlGetContent(read,args[1]);
                                    String uuid = PlayerEmail.selectUuid(con, args[1]);
                                    if (MailWApi.sendEmail(plugin, uuid, sd, tt, content)) {
                                        sender.sendMessage(ChatColor.GREEN+"§lMail Sent Successfully");
                                        return true;
                                    } else {
                                        sender.sendMessage(ChatColor.RED+"§lFailed To Send The Email, The Player Does NOT Exist OR Does NOT Set Up = MailW = OR The Administrator's Sending Mailbox Is Set Incorrectly");
                                        return true;
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.RED+"§lEmail Sent Failed, HTML Parsing Error");
                                    return true;
                                }
                            } catch (Exception e) {
                                sender.sendMessage(ChatColor.RED+"§lEmail Sent Failed, Player Does NOT Exist OR Player Is NOT Set = MailW =");
                                return true;
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED+"§lNo Such Template");
                            return true;
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return true;
                    }
                } else {
                    sender.sendMessage(ChatColor.RED+"§lYou Don't Have Permission");
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
            subCommands.add("send");
            subCommands.add("help");
            return subCommands;
        }
        if (args[0].equalsIgnoreCase("set") && args.length == 2) {
            List<String> example = new ArrayList<>();
            example.add("noreply@example.com smtp.example.com 25 password");
            return example;
        }
        if (args[0].equalsIgnoreCase("test") && args.length == 2) {
            List<String> playersNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (Player player : players) {
                playersNames.add(player.getName());
            }
            return playersNames;
        }
        if (args[0].equalsIgnoreCase("send")) {
            if (args.length == 2) {
                List<String> playersNames = new ArrayList<>();
                Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
                Bukkit.getServer().getOnlinePlayers().toArray(players);
                for (Player player : players) {
                    playersNames.add(player.getName());
                }
                return playersNames;
            }
            //ToDo 文件夹索引
        }
        return null;
    }
}
