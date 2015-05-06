/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import frame.ShowFileDelete;

/**
 *
 * @author nhuan
 */
public class DeleteAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        //TrashService tService = new TrashService();
    	ShowFileDelete delete = new ShowFileDelete();
    	delete.setVisible(true);
    }
    
}
