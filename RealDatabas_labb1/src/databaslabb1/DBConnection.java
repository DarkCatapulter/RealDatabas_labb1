/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaslabb1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author swehu
 */
public class DBConnection {

    Connection con = null;
    private String loggedInUser = ""; 

    public DBConnection() throws ClassNotFoundException, SQLException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            //java.sql.Driver d=new com.mysql.jdbc.Driver();
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dblabb1?useUnicode=true&characterEncoding=UTF-8", "dblabb1", "FatihSover");
            System.out.println("Connected!");

        } /*finally {
            try {
                if (con != null) {
                    con.close();
                    System.out.println("Connection closed.");
                }
            } */ catch (SQLException e) {
            System.out.println(e);

        }
    }

    public void close() {
        try {
            if (con != null) {
                con.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void executeQuery(String query) throws SQLException {
        Statement stmt = null;
        try {             // Execute the SQL statement             
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Get the attribute names             
            ResultSetMetaData metaData = rs.getMetaData();
            int ccount = metaData.getColumnCount();
            for (int c = 1; c <= ccount; c++) {
                System.out.print(metaData.getColumnName(c) + "\t");
            }
            System.out.println();
            // Get the attribute values             
            while (rs.next()) {                 // NB! This is an example, -not- the preferred way to retrieve data.                 // You should use methods that return a specific data type, like                 // rs.getInt(), rs.getString() or such.                 // It's also advisable to store each tuple (row) in an object of                 // custom type (e.g. Employee).                 
                for (int c = 1; c <= ccount; c++) {
                    System.out.print(rs.getObject(c) + "\t");
                }
                System.out.println();
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
    public void setUser(String email){
        if(loggedInUser.isEmpty())loggedInUser = email;
    }
    public String getUser(){
        return loggedInUser;
    }
}
