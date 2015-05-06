package actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.FileChange;
import mydropbox.MyDropboxSwing;
import service.TransactionService;
import controller.AppConfig;

public class CommitAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//Lay tid gan nhat
		int tid = TransactionService.getTransaction()+1;
		//Luu tid vao file property
		System.out.println("Transaction: "+tid);
		//		while(!MyDropboxSwing.lstCommit.isEmpty())
//		{
//			FileChange fileChange = MyDropboxSwing.lstCommit.poll();
//			fileChange.setTid(tid);
//			System.out.println(fileChange.toString());
//			fileChange.doAction();
//		}
		while(!MyDropboxSwing.lstCommit.isEmpty())
		{
			FileChange fileChange  = MyDropboxSwing.lstCommit.poll();
			if(fileChange!=null)
			{
				fileChange.setTid(tid);
//				System.out.println(fileChange.toString());
				fileChange.doAction();
//				Thread fileUpload = new Thread(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						fileChange.doAction();
//					}
//				});
//				fileUpload.start();
			}
		}
		AppConfig property = MyDropboxSwing.config;
		property.write("tid", Integer.toString(MyDropboxSwing.cursor.getTid()));
		property.write("index", Integer.toString(MyDropboxSwing.cursor.getIndex()));
		MyDropboxSwing.jTextArea1.append("\n Sync completed");
		System.gc();
	}

}
