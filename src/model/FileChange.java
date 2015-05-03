package model;

import java.util.Date;

public abstract class FileChange implements Runnable{

	private int fileChangeId;
	private int fileId;
	private String fileName;
	private String tid;
	private int type;
	private String beforeChange;
	private String afterChange;
	private Date timestamp;
	private int isFile;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getIsFile() {
		return isFile;
	}

	public void setIsFile(int isFile) {
		this.isFile = isFile;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public int getFileChangeId() {
		return fileChangeId;
	}

	public void setFileChangeId(int fileChangeId) {
		this.fileChangeId = fileChangeId;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getBeforeChange() {
		return beforeChange;
	}

	public void setBeforeChange(String beforeChange) {
		this.beforeChange = beforeChange;
	}

	public String getAfterChange() {
		return afterChange;
	}

	public void setAfterChange(String afterChange) {
		this.afterChange = afterChange;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "File "+this.getFileName()+" is "+this.getType();
	}

	public abstract void doAction();
	public abstract void doUpdate();
}
