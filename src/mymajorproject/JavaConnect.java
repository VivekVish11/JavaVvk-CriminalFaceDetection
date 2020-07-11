/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymajorproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author LENOVO
 */
public class JavaConnect {

    public Statement st;
    public ResultSet rs;
    Connection conn = null;

    public static Connection ConnectDb() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/faceidentification?zeroDateTimeBehavior=convertToNull", "root", "");
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }

    public void disConnect() {
        try {
            conn.close();
        } catch (SQLException e) {
        }
    }

    public void ExecutSQL(String SQL) {
        try {
            st = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.executeQuery(SQL);

        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }

    void executsSQL(String select__from_Persion_) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void executSQL(String select__from_Person_order_by_id_desc_limi) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
