package model;

public class FileUpdate extends FileCreate {

	public FileUpdate(String fileName,
			int isFile) {
		super(fileName, isFile);
		this.setType(Constants.UPDATE);
	}

	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		super.doAction();
	}

	@Override
	public void doUpdate() {
		// TODO Auto-generated method stub
		super.doUpdate();
	}
}
