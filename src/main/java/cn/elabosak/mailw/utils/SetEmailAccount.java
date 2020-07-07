package cn.elabosak.mailw.utils;

import cn.elabosak.mailw.api.MailWApi;
import cn.elabosak.mailw.sql.PlayerEmail;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author ElaBosak
 */
public class SetEmailAccount {
    public static boolean set(String playerName, String uuid, String email) {
        try {
            Connection con = MailWApi.getConnection();
            boolean b = false;
            if (PlayerEmail.selectEmail(con, uuid) != null) {
//                b = sqlite.updateEmail(con, uuid, email) && sqlite.updatePlayerName(con, uuid, playerName);
                b = PlayerEmail.update(con, uuid, email, playerName);
//                b = sqlite.delete(con,uuid) && sqlite.insert(con, playerName, uuid, email);
                con.close();
            } else {
                b = PlayerEmail.insert(con, playerName, uuid, email);
                con.close();
            }
            return b;
        } catch (SQLException e) {
            return false;
        }
    }
    public static boolean remove(String uuid) {
        try {
            Connection con = MailWApi.getConnection();
            if (PlayerEmail.delete(con, uuid)) {
                con.close();
                return true;
            } else {
                con.close();
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }
}
