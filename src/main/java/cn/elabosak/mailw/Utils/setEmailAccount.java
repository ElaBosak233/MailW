package cn.elabosak.mailw.Utils;

import cn.elabosak.mailw.SQL.SQLite;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public class setEmailAccount {
    public boolean set(UUID uuid, String email) {
        try {
            SQLite sqlite = new SQLite();
            Connection con = sqlite.getConnection();
            sqlite.createTable(con);
            sqlite.insert(con, uuid, email);
            con.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
