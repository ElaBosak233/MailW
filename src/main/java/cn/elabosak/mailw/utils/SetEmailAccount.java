package cn.elabosak.mailw.utils;

import cn.elabosak.mailw.sql.PlayerEmail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

/**
 * @author ElaBosak
 */
public class SetEmailAccount {
    public boolean set(UUID uuid, String email) {
        try {
            PlayerEmail sqlite = new PlayerEmail();
            Connection con = sqlite.getConnection();
            boolean b = false;
            if (sqlite.select(con, uuid.toString()) != null) {
                b = sqlite.update(con, uuid, email);
            } else {
                b = sqlite.insert(con, uuid, email);
            }
            con.close();
            return b;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean remove(UUID uuid) {
        try {
            PlayerEmail sqlite = new PlayerEmail();
            Connection con = sqlite.getConnection();
            sqlite.delete(con, uuid);
            con.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
