package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

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
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		if(stream!=null) 
		{
			try {
				prop.load(stream);
				stream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
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
			prop.setProperty("address", "localhost");
			prop.setProperty("port", "8112");
			String userDirectory = System.getProperty("user.home")+"/Dropbox";
			String tmpDirectory = FileUtils.getTempDirectory()+"/Dropbox";
			File file = new File(userDirectory);
			File tmp = new File(tmpDirectory);
			try{
				file.mkdir();
				tmp.mkdir();
				prop.setProperty("urls", userDirectory);
				prop.setProperty("tmpFolder", tmpDirectory);
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
