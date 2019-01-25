package com.wangyu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class tab4_t {
    public newform parent;
    String name;
    String contract;
    String phone;
    DefaultTableModel dtm查询区;
    public tab4_t(newform p)
    {
        parent=p;
        dtm查询区=new DefaultTableModel(0, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        parent.tab4查询结果table.setModel(dtm查询区);
        String header[]=new String[]{"用户","联系人","电话"};
        dtm查询区.setColumnIdentifiers(header);
    }
    public void get_input()
    {
        name=parent.tab4用户名input.getText();
        contract=parent.tab4联系人input.getText();
        phone=parent.tab4电话号input.getText();;
        //System.out.println(name+","+type+","+id);
    }
    public void add_contract()
    {
        System.out.println("do_order");
        get_input();
        try {
            parent.statement.executeUpdate(String.format("INSERT INTO EMERGENT_CONTRACT VALUES ('%s','%s','%s')",name,contract,phone) );
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
            return;
        }
    }
    public void del_contract()
    {
        System.out.println("do_order");
        get_input();
        try {
            parent.statement.executeUpdate(String.format("delete from EMERGENT_CONTRACT where custname='%s' and contract_name='%s'",name,contract));
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
            ResultSet rs = parent.statement.executeQuery(String.format("select * from EMERGENT_CONTRACT where custname='%s'",name));
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
            ResultSet rs = parent.statement.executeQuery("select * from EMERGENT_CONTRACT");
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
