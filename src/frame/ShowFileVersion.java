/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.CustomTableModel;

import org.restlet.ext.xml.DomRepresentation;

import service.TrashService;
import service.VersionService;
import model.*;

/**
 *
 * @author nhuan
 */
public class ShowFileVersion extends javax.swing.JFrame {

    /**
     * Creates new form ShowFileDelete
     */
    public JTable table;

    public ShowFileVersion() {
        initComponents();
        //Container pane = getContentPane();
        this.setLayout(new BorderLayout());
        //String[] column = {"File Name", "Date", "Link Download"};
        VersionService trashService = new VersionService();
        DomRepresentation dom = trashService.getVersions();
        XmlFactory factory = new XmlFactory();
        Object[][] lst = null;
        String[] columnName = {"File Name", "Date", "Link Download"};
        if (dom != null) {
            try {
                lst = factory.parseXMLToVersion(dom.getDocument());
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        CustomTableModel model = new CustomTableModel(lst,columnName);
        table = new JTable(model);
        JScrollPane pane = new JScrollPane(table);
        this.add(pane, BorderLayout.CENTER);
        //this.add(pane);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    public static void main(String [] args)
    {
        ShowFileVersion delete = new ShowFileVersion();
        delete.setVisible(true);

    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 732, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 336, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
