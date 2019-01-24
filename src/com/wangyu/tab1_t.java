package com.wangyu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class tab1_t {
    public newform parent;
    DefaultTableModel dtm编辑区,dtm查询区;
    String header[];
    public void handle_choose() //throws Exception
    {
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
        else if(parent.tab1用户信息RadioButton.isSelected())
        {
             header= new String[]{"用户","年龄"};
        }
        else
        {
            System.out.println("错误：没有选项被选中！");
            JOptionPane.showMessageDialog(null, "错误：没有选项被选中！");
            return;
            //throw new RuntimeException("错误：没有选项被选中！");
            //throw new Exception("没有选项被选中！");
        }

        dtm编辑区.setColumnIdentifiers(header);
        dtm查询区.setColumnIdentifiers(header);


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
