package com.wangyu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class newform extends JFrame {
    private JPanel panel1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("newform");
        frame.setContentPane(new newform().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JTabbedPane tabbedPane1;
    private JButton button1;
    private JTextArea asdasdasdasdTextArea;
    private JTabbedPane tabbedPane2;

    {
        String driverName = "oracle.jdbc.OracleDriver"; // for Oracle
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException : "+e.getMessage());
        }
        Connection connection = null;
        String url = "jdbc:oracle:thin:@localhost:1521:orcl"; // for Oracle
        try {
            connection = DriverManager.getConnection(url, "wangyu", "123456");
        }
        catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        Statement stmt = null;
        String query = " SELECT * from table1";
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String id = rs.getString(1); // or rs.getString("NAME");
                String name= rs.getString(2);
                System.out.println(id+name);
            }
            stmt.close();
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }

        System.out.println("done");
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}