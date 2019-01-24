package com.wangyu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class newform extends JFrame {
    public JPanel panel1;
    public JTabbedPane tabbedPane1;
    public JButton button4;
    public JButton button5;
    public JRadioButton tab1航班RadioButton;
    public JRadioButton tab1用户信息RadioButton;
    public JRadioButton tab1旅店RadioButton;
    public JRadioButton tab1车辆RadioButton;
    public JTable tab1操作区table;
    public JTable tab1查询结果table;
    public JButton tab1插入Button;
    public JButton tab1删除Button;
    public JButton tab1更新Button;
    public JButton tab1查询Button;
    public JTextField textField1;
    public JTextArea textArea1;
    public JButton button1;
    public tab1_t tab1;
    public Connection connection = null;

    public newform() {
        tab1= new tab1_t(this);
        tab1.handle_choose();
        tab1航班RadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tab1.handle_choose();
            }
        });
        tab1车辆RadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tab1.handle_choose();
            }
        });
        tab1用户信息RadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tab1.handle_choose();
            }
        });
        tab1旅店RadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tab1.handle_choose();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("数据库作业1");
        frame.setContentPane(new newform().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    { // 建立数据库连接
        String driverName = "oracle.jdbc.OracleDriver"; // for Oracle
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException : " + e.getMessage());
            JOptionPane.showMessageDialog(null, "错误：找不到驱动"+driverName+"!");
            System.exit(1);
        }

        String url = "jdbc:oracle:thin:@localhost:1521:orcl"; // for Oracle
        try {
            connection = DriverManager.getConnection(url, "wangyu", "123456");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "错误：数据库连接建立失败！");
            System.exit(1);
        }

        Statement stmt = null;
        String query = " SELECT * from table1";
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String id = rs.getString(1); // or rs.getString("NAME");
                String name = rs.getString(2);
                System.out.println(id + name);
            }
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("done");
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

}