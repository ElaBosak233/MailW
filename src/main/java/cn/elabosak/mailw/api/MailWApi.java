package cn.elabosak.mailw.api;

import cn.elabosak.mailw.sql.PlayerEmail;
import cn.elabosak.mailw.sql.MailWSettings;
import cn.elabosak.mailw.utils.SendEmail;
import org.bukkit.entity.Player;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ElaBosak
 */
public class MailWApi {

    /**
     * Get The Version Of This Plugin
     */
    public static String getVersion() {
        return "1.1.0";
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
            PlayerEmail sqlite = new PlayerEmail();
            Connection con = MailWApi.getConnection();
            receiveMailAccount = sqlite.select(con,target.getUniqueId().toString());
            if (receiveMailAccount != null) {
                con.close();
                return SendEmail.send(receiveMailAccount,sender,subject,content);
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
        if (file.exists()) {
            StringBuilder result = new StringBuilder();
            try{
                BufferedReader br = new BufferedReader(new FileReader(file));
                String s = null;
                while((s = br.readLine())!=null){
                    result.append(System.lineSeparator()).append(s);
                }
                br.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            return result.toString();
        } else {
            return "err";
        }
    }

    /**
     * Get the title of html as a mail subject
     * @param htmlString Output after converting html file to String
     * @return The title of the html obtained as the mail subject
     */
    public static String getTitle(String htmlString) {
        String regex;
        StringBuilder title = new StringBuilder();
        final List<String> list = new ArrayList<>();
        regex = "<title>.*?</title>";
        final Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
        final Matcher ma = pa.matcher(htmlString);
        while (ma.find())
        {
            list.add(ma.group());
        }
        for (String value : list) {
            title.append(value);
        }
        String result = title.toString().replace("<title>","");
        result = result.replace("</title>","");
        return result;
    }

    /**
     * Get the sender of html as a mail subject
     * @param htmlString Output after converting html file to String
     * @return The sender of the html obtained as the mail sender
     */
    public static String getSender(String htmlString) {
        String regex;
        StringBuilder sender = new StringBuilder();
        final List<String> list = new ArrayList<>();
        regex = "<sender hidden>.*?</sender>";
        final Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
        final Matcher ma = pa.matcher(htmlString);
        while (ma.find())
        {
            list.add(ma.group());
        }
        for (String value : list) {
            sender.append(value);
        }
        String result = sender.toString().replace("<sender hidden>","");
        result = result.replace("</sender>","");
        return result;
    }

    /**
     * Get the content of html as a mail subject
     * @param htmlString Output after converting html file to String
     * @return The content of the html obtained as the mail content
     */
    public static String getContent(String htmlString, Player target) {
        String out = null;
        out = htmlString.replace("{{player.name}}", target.getName());
        out = out.replace("{{player.uuid}}", target.getUniqueId().toString());
        return out;
    }

    /**
     * Get the email of the specified player
     * @param con Database connection data
     * @param player The name of the player whose email needs to be found
     * @return The required player's email
     * @throws SQLException Database error
     */
    public static String getPlayerEmail(Connection con, Player player) throws SQLException {
        PlayerEmail sqlite = new PlayerEmail();
        return sqlite.select(con, player.getUniqueId().toString());
    }

    /**
     * Determine if MailW is set
     * @param con Database connection data
     * @return Whether MailW is set
     * @throws SQLException Database error
     */
    public static boolean isMailWSet(Connection con) throws SQLException {
        return MailWSettings.selectEmail(con) != null && MailWSettings.selectSmtp(con) != null && MailWSettings.selectPort(con) != null && MailWSettings.selectPasswd(con) != null;
    }

    /**
     * Get a database connection
     * @return Database connection data
     * @throws SQLException Database error
     */
    public static Connection getConnection() throws SQLException {
        SQLiteConfig config = new SQLiteConfig();
        config.setSharedCache(true);
        config.enableRecursiveTriggers(true);
        SQLiteDataSource ds = new SQLiteDataSource(config);
        String url = System.getProperty("user.dir");
        ds.setUrl("jdbc:sqlite:"+url+"/plugins/MailW/"+"MailW-Database.db");
        return ds.getConnection();
    }

}
