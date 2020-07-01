package cn.elabosak.mailw.SQL;

import java.sql.*;
import java.util.UUID;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

public class EMAIL {

    public Connection getConnection() throws SQLException {
        SQLiteConfig config = new SQLiteConfig();
        config.setSharedCache(true);
        config.enableRecursiveTriggers(true);
        SQLiteDataSource ds = new SQLiteDataSource(config);
        ds.setUrl("jdbc:sqlite:MailW-Database.db");
        return ds.getConnection();
    }

    //create Table
    public boolean createTable(Connection con)throws SQLException {
        String sql = "create TABLE IF NOT EXISTS EMAIL(uuid String, email String); ";
        Statement stat = null;
        stat = con.createStatement();
        try {
            stat.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    //drop table
    public void dropTable(Connection con)throws SQLException {
        String sql = "drop table EMAIL ";
        Statement stat = null;
        stat = con.createStatement();
        stat.executeUpdate(sql);

    }

    //新增
    public boolean insert(Connection con, UUID uuid, String email)throws SQLException {
        String sql = "insert into EMAIL (uuid,email) values(?,?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            int idx = 1 ;
            pstmt.setString(idx++,uuid.toString());
            pstmt.setString(idx++,email);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    //修改
    public boolean update(Connection con, UUID uuid, String email)throws SQLException {
        String sql = "update EMAIL set email = ? where uuid = ?";
        PreparedStatement pst = null;
        pst = con.prepareStatement(sql);
        int idx = 1 ;
        try {
            pst.setString(idx++, email);
            pst.setString(idx++, uuid.toString());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    //刪除
    public void delete(Connection con,UUID uuid)throws SQLException {
        String sql = "delete from EMAIL where uuid = ?";
        PreparedStatement pst = null;
        pst = con.prepareStatement(sql);
        int idx = 1 ;
        pst.setString(idx++, uuid.toString());
        pst.executeUpdate();

    }

    public static void selectAll(Connection con)throws SQLException {
        String sql = "select * from EMAIL";
        Statement stat = null;
        ResultSet rs = null;
        stat = con.createStatement();
        rs = stat.executeQuery(sql);
        while(rs.next())
        {
            String uuid = rs.getString("uuid");
            String email = rs.getString("email");
//            plugin.data.put(uuid,email);
            System.out.println(rs.getString("uuid")+"\t"+rs.getString("email"));
        }

    }

    public String select(Connection con, String uuid)throws SQLException {
//        String sql = "select * from EMAIL where uuid = '"+uuid+"'";
        String sql = "select * from EMAIL where uuid =?";
        PreparedStatement pst = null;
        ResultSet rs = null;
        pst = con.prepareStatement(sql);
        int idx = 1 ;
        pst.setString(idx++, uuid);
        rs = pst.executeQuery();
        String email = null;
        if (rs.next()) {
            email = rs.getString("email");
            System.out.println(rs.getString("uuid")+"\t"+rs.getString("email"));
        }
        return email;
    }

//    public static void main(String args[]) throws SQLException {
//        Connection con = getConnection();
//        System.out.println(select(con,"58800501-1b7e-4e38-827a-445c724a8fd6"));
//    }

}
