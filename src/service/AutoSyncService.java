package service;

import java.util.Timer;
import java.util.TimerTask;

import javax.management.timer.TimerNotification;

import controller.AppConfig;
import actionListener.SyncAction;
import actionListener.CommitAction;
import model.Constants;
import model.FileCursor;
import mydropbox.MyDropboxSwing;

public class AutoSyncService implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//Lay tid hien tai
		while(true)
		{
			System.out.println("Thuc hien kiem tra");			
			
			SyncAction syncAction = new SyncAction();
			syncAction.sync();
			
			if(MyDropboxSwing.lstCommit.size()>0)
			{
				System.out.println("Thuc hien upload");
				CommitAction upload = new CommitAction();
				upload.actionPerformed(null);
			}
			//So sanh cac file trong muc sync voi file commit, thong bao cac file conflict
			//Thuc hien upload
			//Ghi tid
			//Lap lai trong 3s
			System.out.println("Ket thuc kiem tra");
			try {
				Thread.sleep(Constants.TIME_UPDATE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
