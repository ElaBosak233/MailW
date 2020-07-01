package cn.elabosak.mailw.Utils;

import cn.elabosak.mailw.SQL.EMAIL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public class setEmailAccount {
    public boolean set(UUID uuid, String email) {
        try {
            EMAIL sqlite = new EMAIL();
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
