package com.wangyu;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.DateFormat;
import java.util.Date;

public class newform extends JFrame {
    public JPanel panel1;
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
    public JTextField tab2编号table;
    public JComboBox tab2类型combo;
    public JTextField tab2用户名input;
    public JButton tab2预定button;
    public JButton tab2退订button;
    public JButton tab2查询所有button;
    public JButton tab2查询指定button;
    public JTextField tab3用户名input;
    public JButton tab3查询Button;
    public JTable tab2查询结果table;
    public JTable tab3查询结果table;
    public JButton tab4增加button;
    public JButton tab4删除button;
    public JTextField tab4用户名input;
    public JTextField tab4联系人input;
    public JTextField tab4电话号input;
    public JButton tab4查询所有button;
    public JButton tab4查询指定button;
    public JTable tab4查询结果table;
    public JTabbedPane tabbedPane1;
    public JTextField textField1;
    public JTextArea textArea1;
    public JButton button1;
    public JPanel datepanel;
    public tab1_t tab1;
    public tab2_t tab2;
    public tab3_t tab3;
    public tab4_t tab4;
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
        tab2= new tab2_t(this);
        tab3= new tab3_t(this);
        tab4= new tab4_t(this);
        datepanel.setLayout(new BorderLayout());
        datepanel.add(new ClockPane(), BorderLayout.SOUTH);

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
        tab2预定button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tab2.do_order();
            }
        });
        tab2退订button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tab2.de_order();
            }
        });
        tab2查询所有button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tab2.query_all();
            }
        });
        tab2查询指定button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tab2.query_single();
            }
        });
        tab3查询Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tab3.query_route();
            }
        });

        tab4增加button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tab4.add_contract();
            }
        });
        tab4删除button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tab4.del_contract();
            }
        });
        tab4查询所有button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tab4.query_all();
            }
        });
        tab4查询指定button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tab4.query_single();
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

    class ClockPane extends JPanel {

        private JLabel clock = new JLabel();

        public ClockPane() {
            setLayout(new BorderLayout());
            Font font = new Font("Serif", Font.BOLD, 24);
            clock.setFont(font);

            tickTock();
            add(clock);
            Timer timer = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tickTock();
                }
            });
            timer.setRepeats(true);
            timer.setCoalesce(true);
            timer.setInitialDelay(0);
            timer.start();
        }

        public void tickTock() {
            clock.setText("sa18225349   "+DateFormat.getDateTimeInstance().format(new Date()));
        }
    }
}