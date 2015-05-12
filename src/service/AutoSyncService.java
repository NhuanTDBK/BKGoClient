package service;

import model.Constants;
import mydropbox.MyDropboxSwing;
import actionListener.CommitAction;
import actionListener.SyncAction;

public class AutoSyncService implements Runnable{

   public static SyncAction syncAction = new SyncAction();
   public static CommitAction upload = new CommitAction();
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//Lay tid hien tai
		while(true)
		{
			
			syncAction.sync();
			
			if(!MyDropboxSwing.lstCommit.isEmpty())
			{
				System.out.println("Thuc hien upload");
				upload.actionPerformed(null);
			}
			//So sanh cac file trong muc sync voi file commit, thong bao cac file conflict
			//Thuc hien upload
			//Ghi tid
			//Lap lai trong 3s
			//System.out.println("Ket thuc kiem tra");
			try {
				Thread.sleep(Constants.TIME_UPDATE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
