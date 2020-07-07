package cn.elabosak.mailw.sql;

import java.sql.*;
import java.util.UUID;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

/**
 * @author ElaBosak
 */
public class PlayerEmail {

    /**
     * Create a data table
     * @param con Database connection data
     * @return Whether the table was successfully created
     * @throws SQLException Database error
     */
    public static boolean createTable(Connection con) throws SQLException {
        String sql = "create TABLE IF NOT EXISTS EMAIL(playerName String, uuid String, email String); ";
        Statement stat = null;
        stat = con.createStatement();
        try {
            stat.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Delete the table completely
     * @param con Database connection data
     * @throws SQLException Database error
     */
    public static void dropTable(Connection con) throws SQLException {
        String sql = "drop table EMAIL ";
        Statement stat = null;
        stat = con.createStatement();
        stat.executeUpdate(sql);

    }

    /**
     * Add data to the database
     * @param con Database connection data
     * @param email The email address that needs to be set
     * @return Whether the operation was successful
     * @throws SQLException Database error
     */
    public static boolean insert(Connection con, String playerName, String  uuid, String email) throws SQLException {
        String sql = "insert into EMAIL (playerName, uuid, email) values(?,?,?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            int idx = 1 ;
            pstmt.setString(idx++,playerName);
            pstmt.setString(idx++,uuid);
            pstmt.setString(idx++,email);
            pstmt.executeUpdate();
            con.close();
        }
        return true;
    }

    public static boolean update(Connection con, String uuid, String playerName, String email) throws SQLException {
        String sql = "update EMAIL set playerName = ? , email = ? where uuid = ?";
        PreparedStatement pst = null;
        pst = con.prepareStatement(sql);
        int idx = 1 ;
        try {
            pst.setString(idx++, playerName);
            pst.setString(idx++, email);
            pst.setString(idx++, uuid);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

//    /**
//     * Change the data in the database
//     * @param con Database connection data
//     * @param email The email address that needs to be set
//     * @param uuid The uuid of the email needs to be changed
//     * @return Whether the operation was successful
//     * @throws SQLException Database error
//     */
//    public boolean updateEmail(Connection con, String uuid, String email) throws SQLException {
//        String sql = "update EMAIL set email = ? where uuid = ?";
//        PreparedStatement pst = null;
//        pst = con.prepareStatement(sql);
//        int idx = 1 ;
//        try {
//            pst.setString(idx++, email);
//            pst.setString(idx++, uuid);
//            pst.executeUpdate();
//            return true;
//        } catch (SQLException e) {
//            return false;
//        }
//    }
//
//    /**
//     * Change the data in the database
//     * @param con Database connection data
//     * @param uuid The uuid of the email needs to be changed
//     * @param playerName The current name of the person whose name needs to be updated
//     * @return Whether the operation was successful
//     * @throws SQLException Database error
//     */
//    public boolean updatePlayerName(Connection con, String uuid, String playerName) throws SQLException {
//        String sql = "update EMAIL set playerName = ? where uuid = ?";
//        PreparedStatement pst = null;
//        pst = con.prepareStatement(sql);
//        int idx = 1 ;
//        try {
//            pst.setString(idx++, playerName);
//            pst.setString(idx++, uuid);
//            pst.executeUpdate();
//            return true;
//        } catch (SQLException e) {
//            return false;
//        }
//    }

    /**
     * Delete the data in the database
     * @param con Database connection data
     * @param uuid Uuid of data to be deleted
     */
    public static boolean delete(Connection con, String uuid) {
        try {
        String sql = "delete from EMAIL where uuid = ?";
        PreparedStatement pst = null;
        pst = con.prepareStatement(sql);
        int idx = 1 ;
        pst.setString(idx++, uuid);
        pst.executeUpdate();
        return true;
        } catch (SQLException e) {
            return false;
        }
    }

//    public static void selectAll(Connection con) throws SQLException {
//        String sql = "select * from EMAIL";
//        Statement stat = null;
//        ResultSet rs = null;
//        stat = con.createStatement();
//        rs = stat.executeQuery(sql);
//        while(rs.next())
//        {
//            String uuid = rs.getString("uuid");
//            String email = rs.getString("email");
//            System.out.println(rs.getString("uuid")+"\t"+rs.getString("email"));
//        }
//
//    }

    public static String selectEmail(Connection con, String uuid) throws SQLException {
        String sql = "select email from EMAIL where uuid = ?";
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

    public static String selectPlayerName(Connection con, String uuid) throws SQLException {
        String sql = "select playerName from EMAIL where uuid =?";
        PreparedStatement pst = null;
        ResultSet rs = null;
        pst = con.prepareStatement(sql);
        int idx = 1 ;
        pst.setString(idx++, uuid);
        rs = pst.executeQuery();
        String playerName = null;
        if (rs.next()) {
            playerName = rs.getString("playerName");
            System.out.println(rs.getString("uuid")+"\t"+rs.getString("playerName"));
        }
        return playerName;
    }

    public static String selectUuid(Connection con, String playerName) throws SQLException {
        String sql = "select uuid from EMAIL where playerName =?";
        PreparedStatement pst = null;
        ResultSet rs = null;
        pst = con.prepareStatement(sql);
        int idx = 1 ;
        pst.setString(idx++, playerName);
        rs = pst.executeQuery();
        String uuid = null;
        if (rs.next()) {
            uuid = rs.getString("uuid");
            System.out.println(rs.getString("uuid")+"\t"+rs.getString("uuid"));
        }
        return uuid;
    }

//    public static void main(String args[]) throws SQLException {
//        Connection con = getConnection();
//        System.out.println(select(con,"58800501-1b7e-4e38-827a-445c724a8fd6"));
//    }

}
