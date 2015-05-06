/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import static mydropbox.MyDropboxSwing.jTextArea1;
import static mydropbox.MyDropboxSwing.lstCommit;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;

import model.Constants;
import model.FileCreate;
import model.FileDelete;
import model.FileUpdate;
import mydropbox.MyDropboxSwing;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;

public class FileWatcher implements FileAlterationListener {

	SimpleDateFormat format = new  SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	@Override
	public void onDirectoryChange(File arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDirectoryCreate(File arg0) {
		// TODO Auto-generated method stub
		if(!Files.isSymbolicLink(arg0.toPath())){
			String directoryName = ServerUtil.convertPath(arg0.getAbsolutePath(), MyDropboxSwing.urls);
			String log ="Directory is created "+ arg0.getName()+"\n";
			System.out.println(log);
			jTextArea1.append(log);
			FileCreate fileCreate = new FileCreate(directoryName, Constants.IS_FOLDER);
			lstCommit.add(fileCreate);
		}
	}

	@Override
	public void onDirectoryDelete(File arg0) {
		// TODO Auto-generated method stub
		String directoryName = ServerUtil.convertPath(arg0.getAbsolutePath(), MyDropboxSwing.urls);
		String log ="Directory is delete "+ arg0.getName()+"\n";
		System.out.println(log);
		jTextArea1.append(log);
		FileDelete fileDelete = new FileDelete(directoryName, Constants.IS_FOLDER);
		lstCommit.add(fileDelete);
	}

	@Override
	public void onFileChange(File arg0) {
		
		//System.out.println("File is changed " + arg0.getAbsolutePath() + " and size: "+format.format(arg0.lastModified()));
		String log = "File is changed " + arg0.getAbsolutePath() + " and size: "+format.format(arg0.lastModified()) +"\n";
		String fileName = ServerUtil.convertPath(arg0.getAbsolutePath(), MyDropboxSwing.urls);
		System.out.println(log);
		jTextArea1.append(log);
//		list.clear();
//		list.addAll(diffList);
		FileUpdate fileCreate = new FileUpdate(fileName,Constants.IS_FILE);
		lstCommit.add(fileCreate);
	}

	@Override
	public void onFileCreate(File arg0) {
		String log = "File is created " + arg0.getAbsolutePath() + " and size: "+format.format(arg0.lastModified()) +"\n";
		System.out.println(log);
		jTextArea1.append(log);
		if(!Files.isSymbolicLink(arg0.toPath()))
		{
			String fileName = ServerUtil.convertPath(arg0.getAbsolutePath(), MyDropboxSwing.urls);
			FileCreate fileCreate = new FileCreate(fileName,Constants.IS_FILE);
			lstCommit.add(fileCreate);
		}
	}

	@Override
	public void onFileDelete(File arg0) {
            if(!Files.isSymbolicLink(arg0.toPath()))
		{
			String fileName = ServerUtil.convertPath(arg0.getAbsolutePath(), MyDropboxSwing.urls);
			FileDelete fileDelete = new FileDelete(fileName, Constants.IS_FILE);
			lstCommit.add(fileDelete);
		}
	}

	@Override
	public void onStart(FileAlterationObserver arg0) {

	}

	@Override
	public void onStop(FileAlterationObserver arg0) {

	}
//
//	public void compareArr(){
//		for(int i = 0; i < diffList.size(); i++){
//			for(int j = 0; j < list.size(); j++){
//				if(diffList.get(i).fileKey.equals(list.get(j).fileKey)){
//					if(!diffList.get(i).fileName.equals(list.get(j).fileName)){
//						//System.out.println("File rename:"+list.get(j).name+"->"+diffList.get(i).name);
//						log += "File rename:"+list.get(j).fileName+"->"+diffList.get(i).fileName +"\n";
//						System.out.println(log);
//						jTextArea1.append(log);
//					}
//					if(!diffList.get(i).fileParent.equals(list.get(j).fileParent)){
//						//System.out.println("File move:"+list.get(j).fileParent+"/"+list.get(j).name+"->"+diffList.get(i).fileParent+"/"+diffList.get(i).name);
//						log += "File move:"+list.get(j).fileParent+"/"+list.get(j).fileName+"->"+diffList.get(i).fileParent+"/"+diffList.get(i).fileName +"\n";
//						System.out.println(log);
//						jTextArea1.append(log);
//					}
//				}
//			}
//		}
//	}
//
//	public void addAttrList(String fileParent_dir) {
//		File file = new File(fileParent_dir);
//		BasicFileAttributes attr = null;
//		if(!file.isDirectory()){
//			try {
//				attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
//			} catch (IOException ex) {
//				Logger.getLogger(FileWatcher.class.getName()).log(Level.SEVERE, null, ex);
//			}
//			list.add(new FileAttr(file.getName(), attr.fileKey().toString(), file.getParent()));
//		}else{
//			File[] arr = file.listFiles();
//			for(int i = 0; i < file.listFiles().length; i++){               
//				File child_file = arr[i];
//				if(child_file.isDirectory()){
//					try {
//						attr = Files.readAttributes(child_file.toPath(), BasicFileAttributes.class);
//					} catch (IOException ex) {
//						Logger.getLogger(FileWatcher.class.getName()).log(Level.SEVERE, null, ex);
//					}
//					list.add(new FileAttr(child_file.getName(), attr.fileKey().toString(), child_file.getParent()));
//				}
//				addAttr(child_file.toString());
//			}
//		}
//	}
//
//	public void addAttr(String fileParent_dir) {
//		File file = new File(fileParent_dir);
//		BasicFileAttributes attr = null;
//		if(!file.isDirectory()){
//			try {
//				attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
//			} catch (IOException ex) {
//				Logger.getLogger(FileWatcher.class.getName()).log(Level.SEVERE, null, ex);
//			}
//			diffList.add(new FileAttr(file.getName(), attr.fileKey().toString(), file.getParent()));
//		}else{
//			File[] arr = file.listFiles();
//			for(int i = 0; i < file.listFiles().length; i++){               
//				File child_file = arr[i];
//				if(child_file.isDirectory()){
//					try {
//						attr = Files.readAttributes(child_file.toPath(), BasicFileAttributes.class);
//					} catch (IOException ex) {
//						Logger.getLogger(FileWatcher.class.getName()).log(Level.SEVERE, null, ex);
//					}
//					diffList.add(new FileAttr(child_file.getName(), attr.fileKey().toString(), child_file.getParent()));
//				}
//				addAttr(child_file.toString());
//			}
//		}
//	}
}