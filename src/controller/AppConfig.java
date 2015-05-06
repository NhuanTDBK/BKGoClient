package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.JOptionPane;

import model.Constants;
import mydropbox.MyDropboxSwing;

import org.apache.commons.io.FileUtils;

public class AppConfig {
	public Properties getProperties()
	{
		Properties prop = new Properties();
		FileInputStream stream=null;
		try {
			stream = new FileInputStream(Constants.FILEPROPNAME);
			prop.load(stream);
			stream.close();

		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			//e2.printStackTrace();
			System.out.println("Create new config file");
			String ipServer = JOptionPane.showInputDialog("Type IP Server");
			if(ipServer.isEmpty())
				ipServer = "localhost";
			
			File properties = new File(Constants.FILEPROPNAME);
			try {
				properties.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			OutputStream out=null;
			try {
				out = new FileOutputStream(properties);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			prop.setProperty("tid", "0");
			prop.setProperty("index", "0");
			prop.setProperty("protocol", "http");
			prop.setProperty("address",ipServer);
			prop.setProperty("port", "8112");
			String userDirectory = System.getProperty("user.home")+"/Dropbox";
			String tmpDirectory = FileUtils.getTempDirectory()+"/Dropbox";
			String trashDirectory = System.getProperty("user.home")+"/Trash";
			File file = new File(userDirectory);
			File tmp = new File(tmpDirectory);
			File trash = new File(trashDirectory);
			try{
				file.mkdir();
				tmp.mkdir();
				trash.mkdir();
				prop.setProperty("urls", userDirectory);
				prop.setProperty("tmpFolder", tmpDirectory);
				prop.setProperty("trash", trashDirectory);
			}catch(SecurityException ex)
			{
				System.out.print("Khong co quyen tao folder");
			}
			try {
				prop.store(out, "Config");
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return prop;
	}
	public void write(String key,String value)
	{
		try {
			OutputStream out = new FileOutputStream(Constants.FILEPROPNAME);
			MyDropboxSwing.prop.setProperty(key, value);
			MyDropboxSwing.prop.store(out, "config");
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String read(String key)
	{
		return MyDropboxSwing.prop.getProperty(key);
	}
}
