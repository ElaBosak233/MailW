package cn.elabosak.mailw.utils;

import cn.elabosak.mailw.api.MailWApi;
import cn.elabosak.mailw.sql.PlayerEmail;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author ElaBosak
 */
public class SetEmailAccount {
    public static boolean set(String playerName, String uuid, String email) throws SQLException{
        Connection con = MailWApi.getConnection();
        try {
            boolean b = false;
            if (PlayerEmail.selectEmail(con, uuid) != null) {
                b = PlayerEmail.update(con, uuid, playerName, email);
                con.close();
            } else {
                b = PlayerEmail.insert(con, playerName, uuid, email);
                con.close();
            }
            return b;
        } catch (SQLException e) {
            return false;
        } finally{
            con.close();
        }
    }
    public static boolean remove(String uuid) throws SQLException{
        Connection con = MailWApi.getConnection();
        try {
            if (PlayerEmail.delete(con, uuid)) {
                con.close();
                return true;
            } else {
                con.close();
                return false;
            }
        } catch (SQLException e) {
            return false;
        } finally {
            con.close();
        }
    }
//    public static void main(String[] args) throws SQLException{
//        Connection con = MailWApi.getConnection();
//        System.out.println(PlayerEmail.update(con, "58800501-1b7e-4e38-827a-445c724a8fd6", "ElaBosak233", "ElaBosak233@163.com"));
//        con.close();
//    }
}
