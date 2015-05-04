package actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import model.FileChange;
import model.FileCursor;
import model.XmlFactory;
import mydropbox.MyDropboxSwing;

import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;

import service.TransactionService;

public class SyncAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		this.sync();
	}
	public void sync()
	{
		String tid = MyDropboxSwing.prop.getProperty("tid");
		int index = 0;
		//parse thanh list cac doi tuong file change
		//Kiem tra file do co thuoc danh muc commit hay khong?
		//Neu co, bao conflict
		//thuc hien doUpdate
		//Update xong, khoi dong lai watcher
		TransactionService transactionHTTP = new TransactionService();
		String check = "Kiem tra version \n";
//		MyDropboxSwing.jTextArea1.append(check);
		System.out.println(check);
		MyDropboxSwing.jProgressBar1.setIndeterminate(true);
		DomRepresentation dom = transactionHTTP.getLatestUpdate(MyDropboxSwing.cursor);
		if(dom!=null)
		{
			XmlFactory factory = new XmlFactory(dom);
			Document doc=null;
			List<FileChange> lst=null;
			try {
				doc = dom.getDocument();
				lst = factory.parseXML(doc);
				try {
					String log = "Thuc hien sync \n";
					MyDropboxSwing.jTextArea1.append(log);
					System.out.println(log);
					MyDropboxSwing.watcher.stop();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for(FileChange fileChange:lst)
				{
					fileChange.doUpdate();
					MyDropboxSwing.cursor.setTid(Integer.parseInt(fileChange.getTid()));
					MyDropboxSwing.cursor.setIndex(fileChange.getFileChangeId());
				}
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
				System.out.println("Last version!!!!");
			}
			MyDropboxSwing.property.write("tid",Integer.toString(MyDropboxSwing.cursor.getTid()));
			MyDropboxSwing.property.write("index",Integer.toString(MyDropboxSwing.cursor.getIndex()));
			try {
				MyDropboxSwing.watcher.start();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		MyDropboxSwing.jProgressBar1.setIndeterminate(false);
		MyDropboxSwing.jTextArea1.append("Sync completed \n");
		System.out.println("Last version!!!!");
	}
}
