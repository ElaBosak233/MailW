package cn.elabosak.mailw;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author ElaBosak233
 */
public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN+"= MailW Has Been Launched =");
    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"= MailW Has Been Stopped =");
    }

}
