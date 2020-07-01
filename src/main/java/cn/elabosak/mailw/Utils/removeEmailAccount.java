package cn.elabosak.mailw.Utils;

import cn.elabosak.mailw.SQL.EMAIL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public class removeEmailAccount {
    public boolean remove(UUID uuid) {
        try {
            EMAIL sqlite = new EMAIL();
            Connection con = sqlite.getConnection();
            sqlite.delete(con, uuid);
            con.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
