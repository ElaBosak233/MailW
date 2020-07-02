package cn.elabosak.mailw.API;

import cn.elabosak.mailw.SQL.EMAIL;
import cn.elabosak.mailw.Utils.sendEmail;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import cn.elabosak.mailw.Utils.varExchange;

/**
 * @author ElaBosak
 */
public class MailWAPI {

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
     * @return Whether sending mail is successful
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

    /**
     * Get String converted from html file
     * @param file html source file
     * @return Output after converting html file to String
     */
    public static String readHtml(File file) {
        return varExchange.readHtml(file);
    }

    /**
     * Get the title of html as a mail subject
     * @param htmlString Output after converting html file to String
     * @return The title of the html obtained as the mail subject
     */
    public static String getTitle(String htmlString) {
        return varExchange.getTitle(htmlString);
    }

    /**
     * Get the sender of html as a mail subject
     * @param htmlString Output after converting html file to String
     * @return The sender of the html obtained as the mail sender
     */
    public static String getSender(String htmlString) {
        return varExchange.getSender(htmlString);
    }

    /**
     * Get the content of html as a mail subject
     * @param htmlString Output after converting html file to String
     * @return The content of the html obtained as the mail content
     */
    public static String getContent(String htmlString, Player target) {
        return varExchange.deal(htmlString, target);
    }

}
