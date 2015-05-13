package actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.FileChange;
import mydropbox.MyDropboxSwing;
import service.SyncService;
import controller.AppConfig;

public class CommitAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//Lay tid gan nhat
		int tid = SyncService.getTransaction()+1;
		//Luu tid vao file property
		System.out.println("Transaction: "+tid);
		MyDropboxSwing.jProgressBar1.setIndeterminate(true);
		while(!MyDropboxSwing.lstCommit.isEmpty())
		{
			FileChange fileChange  = MyDropboxSwing.lstCommit.poll();
			if(fileChange!=null)
			{
				fileChange.setTid(tid);
				fileChange.doAction();
			}
		}
		MyDropboxSwing.config.write("tid", Integer.toString(MyDropboxSwing.cursor.getTid()));
		MyDropboxSwing.config.write("index", Integer.toString(MyDropboxSwing.cursor.getIndex()));
		MyDropboxSwing.jTextArea1.append("\n Upload completed");
		MyDropboxSwing.jProgressBar1.setIndeterminate(false	);
		//System.gc();
	}
	public void commit()
	{
		this.actionPerformed(null);
	}

}
