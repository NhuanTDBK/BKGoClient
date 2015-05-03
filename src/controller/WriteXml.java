/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import model.Constants;
import model.FileStorage;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import service.UploadService;

/**
 *
 * @author hoàng
 */
public class WriteXml {
	public Element rootElement;
	public File dropBoxPath;
	public String tid;
	public SimpleDateFormat format;
	public WriteXml(String filePath)
	{
		this.dropBoxPath = new File(filePath);
		format = new  SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	}
	public WriteXml(String filePath, String tid)
	{
		this.dropBoxPath = new File(filePath);
		this.tid = tid;
	}
	public void lietkeDir(File file, Element element, Document doc) throws DOMException, NoSuchAlgorithmException, FileNotFoundException, IOException{

		if(!file.isDirectory()){
			Element child_element = doc.createElement("File");

			Attr nameElement = doc.createAttribute("name");
			String fileName = file.getAbsolutePath().replaceAll(dropBoxPath.getPath()+"/","");
			nameElement.setValue(fileName);
			child_element.setAttributeNode(nameElement);

			Attr sizeElement = doc.createAttribute("size");
			sizeElement.setValue(String.valueOf(file.length() / 1024.0));
			child_element.setAttributeNode(sizeElement);

			Attr modifyElement = doc.createAttribute("lastModified");
			modifyElement.setValue(format.format(file.lastModified()));
			child_element.setAttributeNode(modifyElement);

			Attr signElement = doc.createAttribute("signature");
			String fileHash = ServerUtil.encryptFile(new FileInputStream(file));
			signElement.setValue(fileHash);
			child_element.setAttributeNode(signElement);

			
			int fileId = 0;
			try
			{
				UploadService upload = new UploadService();
				//fileId = upload.uploadFile(fileName, fileHash,tid);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			Attr idElement = doc.createAttribute("id");
			idElement.setValue(Integer.toString(fileId));
			child_element.setAttributeNode(idElement);
			
			element.appendChild(child_element);
		}else{
			File[] arr = file.listFiles();
			for(int i = 0; i< file.listFiles().length; i++){    
				File child_file = arr[i];
				Element child_element;
				Attr child_attr;
				if(child_file.isDirectory()){
					child_element = doc.createElement("Directory");
					child_attr = doc.createAttribute("name");
					String directoryName = arr[i].getAbsolutePath().replaceAll(dropBoxPath.getPath()+"/","");
					child_attr.setValue(directoryName);
					child_element.setAttributeNode(child_attr);
					
					int fileId = 0;
					try
					{
						//fileId = UploadService.uploadDirectory(directoryName);
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
					Attr idElement = doc.createAttribute("id");
					idElement.setValue(Integer.toString(fileId));
					child_element.setAttributeNode(idElement);
					element.appendChild(child_element);
				}
				lietkeDir(child_file, rootElement, doc);

			}

		}
	}
	public void lietkeDir(File file, Element element, Document doc,boolean upload) throws DOMException, NoSuchAlgorithmException, FileNotFoundException, IOException{

		if(!file.isDirectory()){
			Element child_element = doc.createElement("File");

			Attr nameElement = doc.createAttribute("name");
			String fileName = file.getAbsolutePath().replaceAll(dropBoxPath.getPath()+"/","");
			nameElement.setValue(fileName);
			child_element.setAttributeNode(nameElement);

			Attr sizeElement = doc.createAttribute("size");
			sizeElement.setValue(String.valueOf(file.length() / 1024.0));
			child_element.setAttributeNode(sizeElement);

			Attr modifyElement = doc.createAttribute("lastModified");
			modifyElement.setValue(format.format(file.lastModified()));
			child_element.setAttributeNode(modifyElement);

			Attr signElement = doc.createAttribute("signature");
			String fileHash = ServerUtil.encryptFile(new FileInputStream(file));
			signElement.setValue(fileHash);
			child_element.setAttributeNode(signElement);			
			element.appendChild(child_element);
		}else{
			File[] arr = file.listFiles();
			for(int i = 0; i< file.listFiles().length; i++){    
				File child_file = arr[i];
				Element child_element;
				Attr child_attr;
				if(child_file.isDirectory()){
					child_element = doc.createElement("Directory");
					child_attr = doc.createAttribute("name");
					String directoryName = arr[i].getAbsolutePath().replaceAll(dropBoxPath.getPath()+"/","");
					child_attr.setValue(directoryName);
					child_element.setAttributeNode(child_attr);
					element.appendChild(child_element);
				}
				lietkeDir(child_file, rootElement, doc,true);
			}
		}
	}
	public static Set<FileStorage> listFilesToSet(Set<FileStorage>hSet,File parent)
	{
		
		if(!parent.isDirectory()){
			FileStorage fs = new FileStorage();
			fs.setFileName(parent.getName());
			fs.setFileSize(parent.length());
			fs.setIsFile(Constants.IS_FILE);
			try {
				fs.setFileHash(ServerUtil.encryptFile(new FileInputStream(parent)));
			} catch (NoSuchAlgorithmException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				hSet.add(fs);
			}
		}else{
			File[] arr = parent.listFiles();
			for(int i = 0; i< parent.listFiles().length; i++){    
				File child_file = arr[i];
				if(child_file.isDirectory()){
					FileStorage fs = new FileStorage();
					fs.setFileName(child_file.getName());
					fs.setIsFile(Constants.IS_FOLDER);
					hSet.add(fs);
				}
				listFilesToSet(hSet,child_file);
			}
		}
		
		return hSet;
	}
	public void writexml(boolean upload) throws IOException, DOMException, NoSuchAlgorithmException{
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			rootElement = doc.createElement("Directories");
			doc.appendChild(rootElement);
			Attr root_attr = doc.createAttribute("name");
			root_attr.setValue(dropBoxPath.getName());
			rootElement.setAttributeNode(root_attr);
			Date begin = new Date();
			System.out.println("Time begin: "+begin.toString());
			if(upload==true)
				lietkeDir(dropBoxPath, rootElement, doc);
			else
				lietkeDir(dropBoxPath, rootElement, doc, true);
			Date end = new Date();
			System.out.println("Time end: "+end.toString());

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			File out_put = new File("xml_demo.xml");
			out_put.createNewFile();
			StreamResult result = new StreamResult(out_put);

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
	public static void main(String [] args)
	{
		HashSet<FileStorage> hSet = new HashSet<FileStorage>();
		listFilesToSet(hSet,new File("/home/nhuan/Dropbox"));
		System.out.println(hSet.size());
	}
}
