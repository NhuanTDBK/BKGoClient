package model;
import java.util.Date;

public class FileStorage {
	
	private int fileId;
	private String fileName;
	private String fileHash;

	private Date createDate;
	private int isFile;
	private float fileSize;
	

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}



	public String getFileHash() {
		return fileHash;
	}

	public void setFileHash(String fileHash) {
		this.fileHash = fileHash;
	}


	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getIsFile() {
		return isFile;
	}

	public void setIsFile(int isFile) {
		this.isFile = isFile;
	}

	public float getFileSize() {
		return fileSize;
	}

	public void setFileSize(float fileSize) {
		this.fileSize = fileSize;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		boolean isEqual=true;
		if(obj instanceof FileStorage)
		{
			FileStorage fileStorage = (FileStorage)obj;
			isEqual = isEqual & (fileStorage.getFileName().equals(this.getFileName()));
			if(!this.getFileHash().isEmpty())
			{
				isEqual = isEqual & (fileStorage.getFileHash().equals(this.getFileHash()));
			}
		}
		return isEqual;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	
}
