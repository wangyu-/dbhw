package com.wangyu;

import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class tab3_t {
    public newform parent;
    DefaultTableModel dtm查询区;
    public tab3_t(newform p)
    {
        parent=p;
        dtm查询区=new DefaultTableModel(0, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        parent.tab3查询结果table.setModel(dtm查询区);
        String header[]=new String[]{"出发城市","到达城市"};
        dtm查询区.setColumnIdentifiers(header);
    }

    public void query_route()
    {
        System.out.println("query_single");
        String name=parent.tab3用户名input.getText();
        dtm查询区.setRowCount(0);
        try {
            ResultSet rs = parent.statement.executeQuery(String.format("select unique from_city,ariv_city from RESERVATIONS,FLIGHTS where  RESERVATIONS.custname='%s'"
                    +" and RESERVATIONS.resvkey= FLIGHTS.ID",name));
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
