package cn.elabosak.mailw.SQL;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import cn.elabosak.mailw.Utils.base64;

public class SETTINGS {

    public static Connection getConnection() throws SQLException {
        SQLiteConfig config = new SQLiteConfig();
        config.setSharedCache(true);
        config.enableRecursiveTriggers(true);
        SQLiteDataSource ds = new SQLiteDataSource(config);
        ds.setUrl("jdbc:sqlite:MailW-Database.db");
        return ds.getConnection();
    }

    public static boolean initTable(Connection con)throws SQLException {
        String sql = "create TABLE IF NOT EXISTS SETTINGS(email String, smtp String, port String, passwd String); ";
        Statement stat = null;
        stat = con.createStatement();
        try {
            stat.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean updateTable(Connection con)throws SQLException {
        String sql = "DROP TABLE IF EXISTS SETTINGS; create TABLE SETTINGS(email String, smtp String, port String, passwd String); ";
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
        String sql = "drop table SETTINGS ";
        Statement stat = null;
        stat = con.createStatement();
        stat.executeUpdate(sql);
    }

    //新增
    public boolean insert(Connection con, String email, String smtp, String port, String passwd)throws SQLException {
//        String sql = "insert into SETTINGS (email, smtp, port, passwd) values("+email+","+smtp+","+port+","+passwd+","+");";
        String sql = "insert into SETTINGS (email, smtp, port, passwd) values(?,?,?,?);";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            int idx = 1 ;
            pstmt.setString(idx++,email);
            pstmt.setString(idx++,smtp);
            pstmt.setString(idx++,port);
            pstmt.setString(idx++,passwd);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    //刪除
    public void delete(Connection con, String email)throws SQLException {
        String sql = "delete from SETTINGS where email = ?";
        PreparedStatement pst = null;
        pst = con.prepareStatement(sql);
        int idx = 1 ;
        pst.setString(idx++, email);
        pst.executeUpdate();
    }

    public static String selectEmail(Connection con)throws SQLException {
        String sql = "select * from SETTINGS";
        Statement stat = null;
        ResultSet rs = null;
        stat = con.createStatement();
        rs = stat.executeQuery(sql);
        String email = null;
        while(rs.next())
        {
            email = rs.getString("email");
        }
        return email;
    }

    public static String selectSMTP(Connection con)throws SQLException {
        String sql = "select * from SETTINGS";
        Statement stat = null;
        ResultSet rs = null;
        stat = con.createStatement();
        rs = stat.executeQuery(sql);
        String SMTP = null;
        while(rs.next())
        {
            SMTP = rs.getString("smtp");
        }
        return SMTP;
    }

    public static String selectPORT(Connection con)throws SQLException {
        String sql = "select * from SETTINGS";
        Statement stat = null;
        ResultSet rs = null;
        stat = con.createStatement();
        rs = stat.executeQuery(sql);
        String port = null;
        while(rs.next())
        {
            port = rs.getString("port");
        }
        return port;
    }

    public static String selectPASSWD(Connection con)throws SQLException {
        String sql = "select * from SETTINGS";
        Statement stat = null;
        ResultSet rs = null;
        stat = con.createStatement();
        rs = stat.executeQuery(sql);
        String passwd = null;
        while(rs.next())
        {
            passwd = rs.getString("passwd");
        }
        return passwd;
    }

//    public static void main(String args[]) throws SQLException {
//        Connection con = getConnection();
//        createTable(con);
//        System.out.println(insert(con,"noreply@elabosak.cn","smtp.mxhichina.com","25",base64.encoder("xxxx")));
//        System.out.println(selectEmail(con));
//        System.out.println(selectSMTP(con));
//        System.out.println(selectPORT(con));
//        System.out.println(selectPASSWD(con));
//    }

}
