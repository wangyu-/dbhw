package com.wangyu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class tab2_t {
    public newform parent;
    String name;
    String type;
    String id;
    DefaultTableModel dtm查询区;
    public tab2_t(newform p)
    {
        parent=p;
        parent.tab2类型combo.addItem("1.航班");
        parent.tab2类型combo.addItem("2.旅店");
        parent.tab2类型combo.addItem("3.车辆");
        dtm查询区=new DefaultTableModel(0, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        parent.tab2查询结果table.setModel(dtm查询区);
        String header[]=new String[]{"用户","预定类型","编号"};
        dtm查询区.setColumnIdentifiers(header);
    }
    public void get_input()
    {
        name=parent.tab2用户名input.getText();
        type=""+(parent.tab2类型combo.getSelectedIndex()+1);
        id=parent.tab2编号table.getText();
        System.out.println(name+","+type+","+id);
    }
    public void do_order()
    {
        System.out.println("do_order");
        get_input();
        try {
            parent.statement.executeUpdate(String.format("INSERT INTO RESERVATIONS VALUES ('%s',%s,'%s')",name,type,id) );
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
            return;
        }
    }
    public void de_order()
    {
        System.out.println("do_order");
        get_input();
        try {
            parent.statement.executeUpdate(String.format("delete from RESERVATIONS where custname='%s' and RESVKEY='%s'",name,id) );
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
            return;
        }
        System.out.println("de_order");
    }
    public void query_single()
    {
        get_input();
        System.out.println("query_single");
        dtm查询区.setRowCount(0);
        try {
            ResultSet rs = parent.statement.executeQuery(String.format("select * from RESERVATIONS where custname='%s'",name));
            while (rs.next()) {
                int cnt=rs.getMetaData().getColumnCount();
                List<String> row = new ArrayList<String>();
                for(int i=0;i<cnt;i++) {
                    row.add(rs.getString(i+1));
                }
                dtm查询区.addRow(row.toArray());
            }
        } catch (SQLException e ) {

        }
    }
    public void query_all()
    {
        System.out.println("query_box");
        dtm查询区.setRowCount(0);
        try {
            ResultSet rs = parent.statement.executeQuery("select * from RESERVATIONS");
            while (rs.next()) {
                int cnt=rs.getMetaData().getColumnCount();
                List<String> row = new ArrayList<String>();
                for(int i=0;i<cnt;i++) {
                    row.add(rs.getString(i+1));
                }
                dtm查询区.addRow(row.toArray());
            }
        } catch (SQLException e ) {

        }
    }
}
