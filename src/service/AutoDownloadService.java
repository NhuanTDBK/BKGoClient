package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

import model.Constants;
import actionListener.SyncAction;

public class AutoDownloadService implements Runnable{
	private Socket socket;
	public AutoDownloadService() {
	}
	@Override
	public void run() {
		try {
			socket = new Socket(InetAddress.getLocalHost(), 9000);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(true)
		{
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//			while(reader.readLine()!="1")
//			{
//				
//			}
			SyncAction syncAction = new SyncAction();
			syncAction.sync();
			try {
				Thread.sleep(Constants.TIME_UPDATE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{

			}
		}
	}

}
