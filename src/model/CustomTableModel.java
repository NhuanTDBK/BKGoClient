/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author nhuan
 */
public class CustomTableModel extends AbstractTableModel {

    private Object[][] data;
    private String [] columnNames;

   
    public CustomTableModel(Object[][] datas,String [] columnName)
    {
        this.data = datas;
        this.columnNames = columnName;
    }

    public Object[][] getDatas() {
        return data;
    }

    public void setDatas(Object[][] datas) {
        this.data = datas;
    }

    public String[] getColumnName() {
        return columnNames;
    }

    public void setColumnName(String[] columnName) {
        this.columnNames = columnName;
    }
    
    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }
     @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
