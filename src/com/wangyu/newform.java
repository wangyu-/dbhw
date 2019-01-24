package com.wangyu;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
    public Connection connection;
    public Statement statement;

    public Connection connection_trans;
    public Statement statement_trans;
    {
        tab1查询结果table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
    }
    public newform() {
        tab1查询结果table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
        tab1插入Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tab1操作区table.isEditing())
                    tab1操作区table.getCellEditor().stopCellEditing();
                tab1.handle_insert();
            }
        });
        tab1查询Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tab1.handle_list();
            }
        });

        tab1查询结果table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                tab1.handle_select_row();
            }
        });
        tab1删除Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tab1操作区table.isEditing())
                    tab1操作区table.getCellEditor().stopCellEditing();
                tab1.handle_delete();
            }
        });
        tab1更新Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tab1操作区table.isEditing())
                    tab1操作区table.getCellEditor().stopCellEditing();
                tab1.handle_update();
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
            connection_trans = DriverManager.getConnection(url, "wangyu", "123456");
            connection_trans.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "错误：数据库连接建立失败！");
            System.exit(1);
        }

        try {
            statement = connection.createStatement();
            statement_trans = connection_trans.createStatement();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "错误：创建statement出错！");
            System.exit(1);
        }

        System.out.println("done");
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

}