package cn.elabosak.mailw.sql;

import java.sql.*;
import java.util.UUID;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

/**
 * @author ElaBosak
 */
public class PlayerEmail {

    public Connection getConnection() throws SQLException {
        SQLiteConfig config = new SQLiteConfig();
        config.setSharedCache(true);
        config.enableRecursiveTriggers(true);
        SQLiteDataSource ds = new SQLiteDataSource(config);
        String url = System.getProperty("user.dir");
        ds.setUrl("jdbc:sqlite:"+url+"/plugins/MailW/"+"MailW-Database.db");
        return ds.getConnection();
    }

    /**
     * Create a data table
     * @param con Database connection data
     * @return Whether the table was successfully created
     * @throws SQLException Database error
     */
    public boolean createTable(Connection con) throws SQLException {
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

    /**
     * Delete the table completely
     * @param con Database connection data
     * @throws SQLException Database error
     */
    public void dropTable(Connection con) throws SQLException {
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
    public boolean insert(Connection con, UUID uuid, String email) throws SQLException {
        String sql = "insert into EMAIL (uuid,email) values(?,?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            int idx = 1 ;
            pstmt.setString(idx++,uuid.toString());
            pstmt.setString(idx++,email);
            pstmt.executeUpdate();
        }
        return true;
    }

    /**
     * Change the data in the database
     * @param con Database connection data
     * @param email The email address that needs to be set
     * @param uuid The uuid of the email needs to be changed
     * @return Whether the operation was successful
     * @throws SQLException Database error
     */
    public boolean update(Connection con, UUID uuid, String email) throws SQLException {
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

    /**
     * Delete the data in the database
     * @param con Database connection data
     * @param uuid Uuid of data to be deleted
     * @throws SQLException Database error
     */
    public void delete(Connection con,UUID uuid) throws SQLException {
        String sql = "delete from EMAIL where uuid = ?";
        PreparedStatement pst = null;
        pst = con.prepareStatement(sql);
        int idx = 1 ;
        pst.setString(idx++, uuid.toString());
        pst.executeUpdate();

    }

    public static void selectAll(Connection con) throws SQLException {
        String sql = "select * from EMAIL";
        Statement stat = null;
        ResultSet rs = null;
        stat = con.createStatement();
        rs = stat.executeQuery(sql);
        while(rs.next())
        {
            String uuid = rs.getString("uuid");
            String email = rs.getString("email");
            System.out.println(rs.getString("uuid")+"\t"+rs.getString("email"));
        }

    }

    public String select(Connection con, String uuid) throws SQLException {
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
