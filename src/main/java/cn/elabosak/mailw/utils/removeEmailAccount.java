package cn.elabosak.mailw.utils;

import cn.elabosak.mailw.sql.Email;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public class removeEmailAccount {
    public boolean remove(UUID uuid) {
        try {
            Email sqlite = new Email();
            Connection con = sqlite.getConnection();
            sqlite.delete(con, uuid);
            con.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
