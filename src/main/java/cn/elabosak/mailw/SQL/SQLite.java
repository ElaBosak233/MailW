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

    }

    //drop table
    public void dropTable(Connection con)throws SQLException {
        String sql = "drop table EMAIL ";
        Statement stat = null;
        stat = con.createStatement();
        stat.executeUpdate(sql);
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

    public static void  selectAll(Connection con)throws SQLException {
        String sql = "select * from EMAIL";
        Statement stat = null;
        ResultSet rs = null;
        stat = con.createStatement();
        rs = stat.executeQuery(sql);
        while(rs.next())
        {
            String uuid = rs.getString("uuid");
            String email = rs.getString("email");
            plugin.data.put(uuid,email);
            System.out.println(rs.getString("uuid")+"\t"+rs.getString("email"));
        }
    }

//    public static void main(String args[]) throws SQLException {
//        SQLite EMAIL = new SQLite();
//        Connection con = EMAIL.getConnection();
//        //建立table
//        EMAIL.createTable(con);
//        //新增資料
//        EMAIL.insert(con, 1, "第一個");
//        EMAIL.insert(con, 2, "第二個");
//        //查詢顯示資料
//        System.out.println("新增二筆資料後狀況:");
//        EMAIL.selectAll(con);
//        //修改資料
//        System.out.println("修改第一筆資料後狀況:");
//        EMAIL.update(con, 1, "這個值被改變了!");
//        //查詢顯示資料
//        EMAIL.selectAll(con);
//        //刪除資料
//        System.out.println("刪除第一筆資料後狀況:");
//        EMAIL.delete(con, 1);
//        //查詢顯示資料
//        EMAIL.selectAll(con);
//        //刪除table
//        EMAIL.dropTable(con);
//        con.close();
//    }

}
