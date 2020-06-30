package cn.elabosak.mailw.SQL;

import java.sql.*;
import java.util.HashMap;
import java.util.UUID;

import cn.elabosak.mailw.Main;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import javax.xml.transform.Result;

public class SQLite {

    static Main plugin;

    public static Connection getConnection() throws SQLException {
        SQLiteConfig config = new SQLiteConfig();
        config.setSharedCache(true);
        config.enableRecursiveTriggers(true);
        SQLiteDataSource ds = new SQLiteDataSource(config);
        ds.setUrl("jdbc:sqlite:MailW-Database.db");
        return ds.getConnection();
    }

    //create Table
    public void createTable(Connection con)throws SQLException {
        String sql = "DROP TABLE IF EXISTS EMAIL ;create table EMAIL (uuid String, email String); ";
        Statement stat = null;
        stat = con.createStatement();
        stat.executeUpdate(sql);
        con.close();
    }

    //drop table
    public void dropTable(Connection con)throws SQLException {
        String sql = "drop table EMAIL ";
        Statement stat = null;
        stat = con.createStatement();
        stat.executeUpdate(sql);
        con.close();
    }

    //新增
    public void insert(Connection con, UUID uuid, String email)throws SQLException {
        String sql = "insert into EMAIL (uuid,email) values(?,?)";
        PreparedStatement pst = null;
        pst = con.prepareStatement(sql);
        int idx = 1 ;
        pst.setString(idx++, uuid.toString());
        pst.setString(idx++, email);
        pst.executeUpdate();
        con.close();
    }

    //修改
    public void update(Connection con, UUID uuid, String email)throws SQLException {
        String sql = "update EMAIL set email = ? where uuid = ?";
        PreparedStatement pst = null;
        pst = con.prepareStatement(sql);
        int idx = 1 ;
        pst.setString(idx++, email);
        pst.setString(idx++, uuid.toString());
        pst.executeUpdate();
        con.close();
    }

    //刪除
    public void delete(Connection con,UUID uuid)throws SQLException {
        String sql = "delete from EMAIL where uuid = ?";
        PreparedStatement pst = null;
        pst = con.prepareStatement(sql);
        int idx = 1 ;
        pst.setString(idx++, uuid.toString());
        pst.executeUpdate();
        con.close();
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
        con.close();
    }

    public static String select(Connection con, String uuid)throws SQLException {
//        String sql = "select * from EMAIL where uuid = '"+uuid+"'";
        String sql = "select * from EMAIL where uuid =?";
        PreparedStatement pst = null;
        pst = con.prepareStatement(sql);
        int idx = 1 ;
        pst.setString(idx++, uuid);
        ResultSet rs = null;
        rs = pst.executeQuery();
        String email = null;
        if (rs.next()) {
            email = rs.getString("email");
            System.out.println(rs.getString("uuid")+"\t"+rs.getString("email"));
        }
        con.close();
        return email;
    }

//    public static void main(String args[]) throws SQLException {
//        Connection con = getConnection();
//        System.out.println(select(con,"58800501-1b7e-4e38-827a-445c724a8fd6"));
//    }

}
