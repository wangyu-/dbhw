package com.wangyu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private JButton button4;
    private JButton button5;
    private JRadioButton 航班RadioButton;
    private JRadioButton 用户信息RadioButton;
    private JRadioButton 旅店RadioButton;
    private JRadioButton 车辆RadioButton;
    private JTable 编辑区table;
    private JTable 查询结果table;
    private JButton 插入Button;
    private JButton 删除Button;
    private JButton 更新Button;
    private JButton 查询Button;

    {

        String[][] data = {
                { "Kundan Kumar Jha", "4031", "CSE" },
                { "Anand Jha", "6014", "IT" }
        };
        JTable tbl = new JTable();
        DefaultTableModel dtm = new DefaultTableModel(0, 0);
        String header[] = new String[] { "Prority", "Task Title", "Start",
                "Pause", "Stop", "Statulses" };

        dtm.setColumnIdentifiers(header);
        //dtm.setRowCount(1);
        编辑区table.setModel(dtm);



        // add row dynamically into the table
        for (int count = 1; count <= 1; count++) {
            dtm.addRow(new Object[] { "data", "data", "data",
                    "data", "data", "data" });
        }

        //编辑区table.setPreferredScrollableViewportSize(编辑区table.getPreferredSize());
        //编辑区table.setFillsViewportHeight(true);

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
    {
        DefaultTableModel dtm = new DefaultTableModel(0, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String header[] = new String[] { "Prority", "Task Title", "Start",
                "Pause", "Stop", "Statulses" };

        dtm.setColumnIdentifiers(header);
        //dtm.setRowCount(1);
        查询结果table.setModel(dtm);

        // add row dynamically into the table
        for (int count = 1; count <= 10; count++) {
            dtm.addRow(new Object[] { "data", "data", "data",
                    "data", "data", "data" });
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}