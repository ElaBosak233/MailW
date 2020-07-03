package cn.elabosak.mailw.utils;

import cn.elabosak.mailw.sql.Email;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

/**
 * @author ElaBosak
 */
public class setEmailAccount {
    public boolean set(UUID uuid, String email) {
        try {
            Email sqlite = new Email();
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
}
