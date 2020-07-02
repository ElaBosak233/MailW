package cn.elabosak.mailw.API;


import cn.elabosak.mailw.Main;
import cn.elabosak.mailw.SQL.EMAIL;
import cn.elabosak.mailw.Utils.sendEmail;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author ElaBosak
 */
public class MailWAPI {

    static Main plugin;

    /*
      Plugin Ver.
     */
    private static final String VERSION = "1.0.0";

    /**
     * Get The Version Of This Plugin
     */
    public static String getVersion() {
        return VERSION;
    }

    /**
     * Send Email
     * @param sender Sender name
     * @param subject Subject of email
     * @param content Content of email
     * @param target Recipient's name
     */
    public static boolean sendEmail(Player target, String sender, String subject, String content) {
        String receiveMailAccount = null;
        try {
            EMAIL sqlite = new EMAIL();
            Connection con = sqlite.getConnection();
            receiveMailAccount = sqlite.select(con,target.getUniqueId().toString());
            if (receiveMailAccount != null) {
                con.close();
                return sendEmail.send(receiveMailAccount,sender,subject,content);
            } else {
                con.close();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
