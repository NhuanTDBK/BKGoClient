package actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import model.FileChange;
import model.XmlFactory;
import mydropbox.MyDropboxSwing;

import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;

import service.TransactionService;

public class SyncAction implements ActionListener {

    private static final TransactionService transactionHTTP = new TransactionService();

    @Override
    public void actionPerformed(ActionEvent e) {
        this.sync();
    }

    public void sync() {

        //parse thanh list cac doi tuong file change
        //Kiem tra file do co thuoc danh muc commit hay khong?
        //Neu co, bao conflict
        //thuc hien doUpdate
        //Update xong, khoi dong lai watcher
        MyDropboxSwing.jProgressBar1.setIndeterminate(true);
        DomRepresentation dom = transactionHTTP.getLatestUpdate(MyDropboxSwing.cursor);
        if (dom != null) {
            XmlFactory factory = new XmlFactory(dom);
            Document doc = null;
            List<FileChange> lst = null;
            try {
                doc = dom.getDocument();
                lst = factory.parseXML(doc);
                try {

                    MyDropboxSwing.watcher.stop();
                } catch (Exception e1) {
                }
                MyDropboxSwing.jTextArea1.setText("");
                String log = "Thuc hien sync \n";
                MyDropboxSwing.jTextArea1.append(log);
                for (FileChange fileChange : lst) {
                    fileChange.doUpdate();
                    MyDropboxSwing.cursor.setTid(fileChange.getTid());
                    MyDropboxSwing.cursor.setIndex(fileChange.getFileChangeId());
                }
            } catch (IOException e2) {
                System.out.println("Last version!!!!");
            }
            MyDropboxSwing.config.write("tid", Integer.toString(MyDropboxSwing.cursor.getTid()));
            MyDropboxSwing.config.write("index", Integer.toString(MyDropboxSwing.cursor.getIndex()));
            MyDropboxSwing.jTextArea1.append("\n Sync completed");
            try {
                MyDropboxSwing.watcher.start();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        MyDropboxSwing.jProgressBar1.setIndeterminate(false);
        System.gc();
    }
}
