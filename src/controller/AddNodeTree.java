/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Bunny
 */
public class AddNodeTree {
    //DefaultTreeModel model = (DefaultTreeModel) mydropbox.MyDropboxSwing.jTree1.getModel();
    public static void addNode(File root_file, DefaultMutableTreeNode root){
        if(!root_file.isDirectory()){
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(root_file.getName());
            root.add(child);
        }else{
            File[] arr = root_file.listFiles();
            for(int i = 0; i < root_file.listFiles().length; i++){               
                File child_file = arr[i];
                System.out.println(child_file.getName());
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(child_file.getName());
                root.add(child);
                addNode(child_file, child);
            }
        }
    }
}
