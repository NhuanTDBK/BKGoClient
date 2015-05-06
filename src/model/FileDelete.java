package model;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import service.DeleteService;
import mydropbox.MyDropboxSwing;

public class FileDelete extends FileCreate implements Runnable{

	public FileDelete(String fileName, int isFile) {
		super(fileName, isFile);
		this.setType(Constants.DELETE);
		// TODO Auto-generated constructor stub
	}

	public FileDelete(int fileId, String fileName, int isFile) {
		super(fileName, isFile);
		this.setType(Constants.DELETE);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		XmlFactory factory = new XmlFactory(MyDropboxSwing.dom);
		int fileId = factory.getFileIdByFileName(this.getFileName());
		this.setFileId(fileId);
		DeleteService.deleteFileByFileName(this.getFileId(), Integer.toString(this.getTid()));
		// Xoa the file trong index xml
		factory.deleteNodeByFileName(this.getFileName());
	}

	@Override
	public void doUpdate() {
		// TODO Auto-generated method stub
		String path = MyDropboxSwing.urls + "/" + this.getFileName();
		Path filePath = Paths.get(path);
		//String userDirectory = System.getProperty("user.home")+"/.local/share/Trash/files/"+this.getFileName();
		Path trash = Paths.get(MyDropboxSwing.trashFolder+"/"+this.getFileName());
		try {
			Files.move(filePath, trash);
			//Files.deleteIfExists(filePath);
		} catch (NoSuchFileException ex) {
			System.out.println("Delete khong thanh cong do khong co file"
					+ ex.getMessage());
		} catch (DirectoryNotEmptyException x) {
			System.err.format("%s not empty%n", path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Delete khong thanh cong do quyen truy cap");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.doUpdate();
	}

}
