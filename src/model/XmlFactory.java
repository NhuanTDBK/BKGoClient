package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import mydropbox.MyDropboxSwing;

import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import controller.ServerUtil;

public class XmlFactory {
	public DomRepresentation dom;
	public SimpleDateFormat format;
	public XmlFactory() {
		dom = MyDropboxSwing.dom;
		format = new  SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	}
	public XmlFactory(DomRepresentation dom) {
		this.dom = dom;
		format = new  SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	}
	public int getFileIdByFileName(String fileName) {
		int fileId = 0;
		String xpath = "//File[@name='" + fileName + "']";
		try {
			Node fileNode = dom.getNode(xpath);

			String idStr = fileNode.getAttributes().getNamedItem("id")
					.getNodeValue();
			fileId = Integer.parseInt(idStr);
		} catch (DOMException doms) {
			doms.printStackTrace();
		}
		catch(NumberFormatException ex)
		{
			ex.printStackTrace();
		}
		return fileId;
	}
	public void insertFileNode(FileChange fileChange)
	{
		try {
			Document doc = dom.getDocument();
			Element fileNode = null;
			if(fileChange.getIsFile()==Constants.IS_FILE)
			{
				fileNode = doc.createElement("File");
				fileNode.setAttribute("id",Integer.toString(fileChange.getFileId()));
				fileNode.setAttribute("name", fileChange.getFileName());
				String filePath = MyDropboxSwing.urls+"/"+fileChange.getFileName();
				File file = new File(filePath);
				fileNode.setAttribute("signature", ServerUtil.encryptFile(new FileInputStream(filePath)));
				Date date = new Date(file.lastModified());
				fileNode.setAttribute("lastModified",format.format(date));
				fileNode.setAttribute("size",String.valueOf(file.length() / 1024.0));
			}
			else 
			{
				fileNode = doc.createElement("Directory");
				fileNode.setAttribute("name", fileChange.getFileName());
				fileNode.setAttribute("id",Integer.toString(fileChange.getFileId()));
			}
			Node root = doc.getFirstChild();
			root.appendChild(fileNode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void deleteNodeByFileId(int fileId)
	{
		try {
			Document doc = MyDropboxSwing.dom.getDocument();
			String express = "//*[@id="+Integer.toString(fileId)+"]";
			Node fileNode =dom.getNode(express);
			Node root = doc.getFirstChild();
			root.removeChild(fileNode);
		} catch (IOException |DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void deleteNodeByFileName(String fileName)
	{
		try {
			Document doc = MyDropboxSwing.dom.getDocument();
			String express = "//*[@name='"+fileName+"']";
			Node fileNode =dom.getNode(express);
			Node root = doc.getFirstChild();
			root.removeChild(fileNode);
		} catch (IOException |DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void saveFileXml(Document doc)
	{
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			doc.setXmlStandalone(true);
			DOMSource source = new DOMSource(doc);
			File out_put = new File("xml_demo.xml");
			out_put.createNewFile();
			StreamResult result = new StreamResult(out_put);

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
			transformer.transform(source, result);
		} catch (IOException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("File saved!");

	}
	public void saveFileXml(Document doc,String fileName)
	{
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			doc.setXmlStandalone(true);
			DOMSource source = new DOMSource(doc);
			File out_put = new File(fileName);
			out_put.createNewFile();
			StreamResult result = new StreamResult(out_put);

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
			transformer.transform(source, result);
		} catch (IOException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("File saved!");

	}
	public List<FileChange>parseXML(Document doc)
	{
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		NodeList nodes = doc.getElementsByTagName("Change");
		List<FileChange> lst= new ArrayList<FileChange>();
		FileChange fileChange = null;
		for(int i = 0;i<nodes.getLength();i++)
		{
			Node node = nodes.item(i);
		
			NamedNodeMap attrs=node.getAttributes();
			String idStr = attrs.getNamedItem("id").getNodeValue();
			int fileId = Integer.parseInt(idStr);
			String isFileStr = attrs.getNamedItem("isFile").getNodeValue();
			int isFile = Integer.parseInt(isFileStr);
			String nameStr = attrs.getNamedItem("name").getNodeValue();
			String tidStr = attrs.getNamedItem("tid").getNodeValue();
			String timestampStr = attrs.getNamedItem("timestamp").getNodeValue();
			String fileChangeIdStr =attrs.getNamedItem("fileChangeId").getNodeValue();
			Date timestamp=null;
			try {
				timestamp = format.parse(timestampStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String typeStr = attrs.getNamedItem("type").getNodeValue();

			if(typeStr.equals("Create"))
			{
				fileChange = new FileCreate(nameStr,isFile);
			}
			else if(typeStr.equals("Update"))
			{
				fileChange = new FileUpdate(nameStr,isFile);
			}
			else if(typeStr.equals("Delete"))
			{
				fileChange = new FileDelete(nameStr,isFile);
			}
			fileChange.setTid(Integer.parseInt(tidStr));
			fileChange.setFileId(fileId);
			fileChange.setTimestamp(timestamp);
			fileChange.setFileChangeId(Integer.parseInt(fileChangeIdStr));
			lst.add(fileChange);
		}
		return lst;
	}
	public Object[][]parseXMLToFile(Document doc)
	{
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		NodeList nodes = doc.getElementsByTagName("File");
		List<FileStorage> lst= new ArrayList<FileStorage>();
		int length = nodes.getLength();
		Object[][] objects = new Object[length][3];
		for(int i = 0;i<length;i++)
		{
			Node node = nodes.item(i);
			FileStorage fileStorage = new FileStorage();
			NamedNodeMap attrs=node.getAttributes();
			String idStr = attrs.getNamedItem("id").getNodeValue();
			int fileId = Integer.parseInt(idStr);

			String nameStr = attrs.getNamedItem("name").getNodeValue();

			String timestampStr = attrs.getNamedItem("timestamp").getNodeValue();
			Date timestamp=null;
			try {
				timestamp = format.parse(timestampStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fileStorage.setFileId(fileId);
			fileStorage.setCreateDate(timestamp);
			fileStorage.setFileName(nameStr);
			objects[i][0] = nameStr;
			objects[i][1] = timestampStr;
			objects[i][2] = "http://"+MyDropboxSwing.address+":"+MyDropboxSwing.port+"/user/"+MyDropboxSwing.userId+"/file/"+fileId;
			lst.add(fileStorage);
		}
		return objects;
	}
	public Object[][] parseXMLToVersion(Document doc)
	{
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		NodeList nodes = doc.getElementsByTagName("File");
		int length = nodes.getLength();
		Object[][] objects = new Object[length][4];
		for(int i = 0;i<length;i++)
		{
			Node node = nodes.item(i);
			NamedNodeMap attrs=node.getAttributes();
			String idStr = attrs.getNamedItem("id").getNodeValue();
			//int fileId = Integer.parseInt(idStr);

			String nameStr = attrs.getNamedItem("name").getNodeValue();

			String timestampStr = attrs.getNamedItem("timestamp").getNodeValue();

			String versionStr = attrs.getNamedItem("version").getNodeValue();

			objects[i][0] = nameStr;
			objects[i][1] = timestampStr;
			objects[i][3] = "http://"+MyDropboxSwing.address+":"+MyDropboxSwing.port+"/user/"+MyDropboxSwing.userId+"/file/"+idStr;
			objects[i][2] = versionStr;
		}
		return objects;
	}
	public static void main(String[] args) {
		XmlFactory fac = new XmlFactory();
		fac.deleteNodeByFileId(29);
	}
}
