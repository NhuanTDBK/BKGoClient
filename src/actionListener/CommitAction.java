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
		int tId = TransactionService.getTransaction()+1;
		String tid = Integer.toString(tId);
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
			final FileChange fileChange  = MyDropboxSwing.lstCommit.poll();
			if(fileChange!=null)
			{
				fileChange.setTid(tid);
				System.out.println(fileChange.toString());
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
//		for(FileChange fileChange:MyDropboxSwing.lstCommit)
//		{
//			fileChange.setTid(tid);
//			System.out.println(fileChange.toString());
//			fileChange.doAction();
//		}
//		MyDropboxSwing.lstCommit.clear();
		AppConfig property = new AppConfig();
		property.write("tid", Integer.toString(MyDropboxSwing.cursor.getTid()));
		property.write("index", Integer.toString(MyDropboxSwing.cursor.getIndex()));
		MyDropboxSwing.jTextArea1.append("\n Sync completed");
//		MyDropboxSwing.jTextArea1.append("\n You have add "+MyDropboxSwing.countFileUpload);
//		MyDropboxSwing.jTextArea1.append("\n You have delete "+MyDropboxSwing.countFileDelete);
		
	}

}
