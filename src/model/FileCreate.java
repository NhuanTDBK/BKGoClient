package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import mydropbox.MyDropboxSwing;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.ClientProtocolException;

import service.DownloadService;
import service.UploadService;

public class FileCreate extends FileChange implements Runnable {

    public FileCreate(String fileName, int isFile) {
        this.setType(Constants.CREATE);
        this.setFileName(fileName);
        this.setIsFile(isFile);
    }

    @Override
    public void doAction() {
        int id = 0;
        try {
            if (this.getIsFile() == Constants.IS_FILE) {
                id = UploadService.uploadFile(this.getFileName(), Integer.toString(this.getTid()));
            } else {
                id = UploadService.uploadDirectory(this.getFileName(), Integer.toString(this.getTid()));
            }
            this.setFileId(id);
            //Append du lieu vao file xml
            XmlFactory fac = new XmlFactory(MyDropboxSwing.dom);
            fac.insertFileNode(this);
            String log = "\n File " + this.getFileName() + " synced on server";
            MyDropboxSwing.jTextArea1.append(log);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void doUpdate() {
        // TODO Auto-generated method stub
        String log;
        if (this.getIsFile() == Constants.IS_FILE) {
            DownloadService download = new DownloadService();
            download.downloadFile(Integer.toString(this.getFileId()), this.getFileName());
            File src = new File(MyDropboxSwing.tmpFolder + "/" + this.getFileName());
            File dst = new File(MyDropboxSwing.urls + "/" + this.getFileName());
            log = "File " + this.getFileName() + " download from server";
            try {
                if (dst.exists()) {
                    dst.delete();
                }
                FileUtils.moveFile(src, dst);
                XmlFactory xml = new XmlFactory(MyDropboxSwing.dom);
                xml.insertFileNode(this);
                MyDropboxSwing.jTextArea1.append(log + "\n");
                
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            String path = MyDropboxSwing.urls + "/" + this.getFileName();
            try {
                Path filePath = Paths.get(path);
                Files.createDirectory(filePath);
                log = "Directory " + this.getFileName() + " create";
                MyDropboxSwing.jTextArea1.append(log + "\n");
            } catch (InvalidPathException ex) {
                System.out.println("Duong dan khong ton tai");
            } catch (FileAlreadyExistsException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("Duong dan da ton tai");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
	//	public void doUpdate(boolean temp)
    //	{
    //		
    //	}

    @Override
    public void run() {
        // TODO Auto-generated method stub
        this.doUpdate();
    }
}
