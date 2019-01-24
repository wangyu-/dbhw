package com.wangyu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class tab1_t {
    public newform parent;
    DefaultTableModel dtm编辑区,dtm查询区;
    String header[];

    void check_vaild()
    {
        if(!parent.tab1航班RadioButton.isSelected()&&!parent.tab1旅店RadioButton.isSelected()&&!parent.tab1车辆RadioButton.isSelected()&&!parent.tab1用户信息RadioButton.isSelected())
        {
            System.out.println("错误：没有选项被选中！");
            JOptionPane.showMessageDialog(null, "错误：没有选项被选中！");
            System.exit(1);
        }
    }

    public void handle_choose() //throws Exception
    {
        check_vaild();
        dtm编辑区.setRowCount(0);
        dtm编辑区.setRowCount(1);
        dtm查询区.setRowCount(0);
        if(parent.tab1航班RadioButton.isSelected())
        {
            header=new String[]{"统一编号","价格","总数","剩余","航班号","出发城市","到达城市"};
        }
        else if(parent.tab1旅店RadioButton.isSelected())
        {
            header=new String[]{"统一编号","价格","总数","剩余","旅店名","城市"};
        }
        else if(parent.tab1车辆RadioButton.isSelected())
        {
            header=new String[]{"统一编号","价格","总数","剩余","车型","城市"};
        }
        else //if(parent.tab1用户信息RadioButton.isSelected())
        {
            header= new String[]{"用户","年龄"};
        }
        dtm编辑区.setColumnIdentifiers(header);
        dtm查询区.setColumnIdentifiers(header);
    }
    public void handle_select_row()
    {
        int r_selected=parent.tab1查询结果table.getSelectedRow();
        System.out.println("r_selected:"+r_selected);
        if (r_selected<= -1) {
            return ;
        }
        int c_cnt=dtm查询区.getColumnCount();
        for (int i=0;i<c_cnt;i++)
        {
            String tmp=dtm查询区.getValueAt(r_selected,i).toString();
            dtm编辑区.setValueAt(tmp,0,i);
        }
    }

    public void handle_list_customers()
    {
        dtm查询区.setRowCount(0);
        try {
            ResultSet rs = parent.statement.executeQuery("select * from CUSTOMERS");
            while (rs.next()) {
                String name = rs.getString(1); // or rs.getString("NAME");
                String age= rs.getString(2);
                List<String> row=new ArrayList<String>();
                row.add(name);
                row.add(age);
                dtm查询区.addRow(row.toArray());
        }
        } catch (SQLException e ) {

        }
    }
    public void handle_delete_customers()
    {
        String name=parent.tab1操作区table.getModel().getValueAt(0,0).toString();
        System.out.println(name);
        try {
            parent.statement.executeUpdate(String.format("DELETE FROM CUSTOMERS WHERE CUSTNAME='%s'",name) );
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
            return;
        }
    }


    public void handle_update_customers()
    {
        String name=parent.tab1操作区table.getModel().getValueAt(0,0).toString();
        String age=parent.tab1操作区table.getModel().getValueAt(0,1).toString();
        System.out.println(name);
        System.out.println(age);
        try {
            parent.statement.executeUpdate(String.format("UPDATE CUSTOMERS SET AGE=%s where CUSTNAME='%s'",age,name) );
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
            return;
        }
    }

    public void handle_insert_customers()
    {
        System.out.println("here");
        String name=parent.tab1操作区table.getModel().getValueAt(0,0).toString();
        String age=parent.tab1操作区table.getModel().getValueAt(0,1).toString();
        System.out.println(name);
        System.out.println(age);
        try {
            parent.statement.executeUpdate(String.format("INSERT INTO CUSTOMERS VALUES ('%s',%s)",name,age) );
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
            return;
        }
    }

    public void handle_delete() //throws Exception
    {
        check_vaild();
        if (parent.tab1用户信息RadioButton.isSelected()) {
            handle_delete_customers();
            return;
        }
        String uni_id=parent.tab1操作区table.getModel().getValueAt(0,0).toString();

        try {
            if(parent.tab1航班RadioButton.isSelected())
            {
                parent.statement_trans.executeUpdate(String.format("delete from FLIGHTS WHERE ID='%s'",uni_id));
            }
            parent.statement_trans.executeUpdate(String.format("delete from RESOURCES where uni_id='%s'",uni_id));
            parent.connection_trans.commit();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
            try {
                parent.connection_trans.rollback();
            }
            catch(SQLException e2)
            {
                JOptionPane.showMessageDialog(null, "错误：rollback失败");
                return;
            }
            return;
        }
    }

    public void handle_update() //throws Exception
    {
        check_vaild();
        if (parent.tab1用户信息RadioButton.isSelected()) {
            handle_update_customers();
            return;
        }
        String uni_id=parent.tab1操作区table.getModel().getValueAt(0,0).toString();
        String price=parent.tab1操作区table.getModel().getValueAt(0,1).toString();
        String num_total=parent.tab1操作区table.getModel().getValueAt(0,2).toString();
        String num_avil=parent.tab1操作区table.getModel().getValueAt(0,3).toString();

        try {
            parent.statement_trans.executeUpdate(String.format("UPDATE RESOURCES SET PRICE=%s,num_total=%s,num_avial=%s where uni_id='%s'",price,num_total,num_avil,uni_id));
            if(parent.tab1航班RadioButton.isSelected())
            {
                String flight_num=parent.tab1操作区table.getModel().getValueAt(0,4).toString();
                String from_city=parent.tab1操作区table.getModel().getValueAt(0,5).toString();
                String ariv_city=parent.tab1操作区table.getModel().getValueAt(0,6).toString();
                parent.statement_trans.executeUpdate(String.format("UPDATE FLIGHTS SET FLIGHT_NUM='%s',FROM_CITY='%s',ARIV_CITY='%s' WHERE ID='%s'",flight_num,from_city,ariv_city,uni_id));
            }
            parent.connection_trans.commit();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
            try {
                parent.connection_trans.rollback();
            }
            catch(SQLException e2)
            {
                JOptionPane.showMessageDialog(null, "错误：rollback失败");
                return;
            }
            return;
        }
    }
    public void handle_list()
    {
        check_vaild();
        if(parent.tab1用户信息RadioButton.isSelected())
        {
            handle_list_customers();
            return;
        }

        dtm查询区.setRowCount(0);
        try {
            ResultSet rs = parent.statement.executeQuery("select UNI_ID,PRICE,NUM_TOTAL,NUM_AVIAL,FLIGHT_NUM,FROM_CITY,ARIV_CITY from RESOURCES,flights where RESOURCES.UNI_ID=FLIGHTS.ID");
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

    public void handle_insert() //throws Exception
    {
        check_vaild();
        if(parent.tab1用户信息RadioButton.isSelected())
        {
            handle_insert_customers();
            return;
        }
        String uni_id=parent.tab1操作区table.getModel().getValueAt(0,0).toString();
        String price=parent.tab1操作区table.getModel().getValueAt(0,1).toString();
        String num_total=parent.tab1操作区table.getModel().getValueAt(0,2).toString();
        String num_avil=parent.tab1操作区table.getModel().getValueAt(0,3).toString();

        try {
            parent.statement_trans.executeUpdate(String.format("INSERT INTO RESOURCES VALUES ('%s',%s,%s,%s)",uni_id,price,num_total,num_avil));
            if(parent.tab1航班RadioButton.isSelected())
            {
                String flight_num=parent.tab1操作区table.getModel().getValueAt(0,4).toString();
                String from_city=parent.tab1操作区table.getModel().getValueAt(0,5).toString();
                String ariv_city=parent.tab1操作区table.getModel().getValueAt(0,6).toString();
                parent.statement_trans.executeUpdate(String.format("INSERT INTO FLIGHTS VALUES ('%s','%s','%s','%s')",uni_id,flight_num,from_city,ariv_city));
            }
            parent.connection_trans.commit();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
            try {
                parent.connection_trans.rollback();
            }
            catch(SQLException e2)
            {
                JOptionPane.showMessageDialog(null, "错误：rollback失败");
                return;
            }
            return;
        }
    }
    public tab1_t(newform p)
    {
        parent=p;
        dtm编辑区=new DefaultTableModel(1, 0);
        dtm查询区=new DefaultTableModel(0, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        parent.tab1查询结果table.setModel(dtm查询区);
        parent.tab1操作区table.setModel(dtm编辑区);
        dtm编辑区.setRowCount(1);
    }

}
