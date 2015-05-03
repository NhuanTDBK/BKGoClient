package service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import model.XmlFactory;
import mydropbox.MyDropboxSwing;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.restlet.data.Form;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import controller.ServerUtil;

public class UploadService {

	public static int uploadFile(String fileName, String tid)
	{
		String url = MyDropboxSwing.protocol+"://"+MyDropboxSwing.address+":"+MyDropboxSwing.port+"/user/"+MyDropboxSwing.userId+"/files/file";
		//String url = "http://localhost:8112/user/1/files/file";
		File file = new File(MyDropboxSwing.urls+"/"+fileName);
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		int fileId = 0;
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		try{
			builder.addBinaryBody("file",file,ContentType.create(Files.probeContentType(Paths.get(file.getPath()))),fileName);
			HttpEntity entity = builder.build();
			httpPost.setEntity(entity);
			httpPost.addHeader("X-TID",tid);
			System.out.println(httpPost.getEntity().toString());
			CloseableHttpResponse response = client.execute(httpPost);
			HttpEntity res = response.getEntity();
			List<String> lst = ServerUtil.getToken(EntityUtils.toString(res));
			fileId = Integer.parseInt(lst.get(0));
			int fileChangeId = Integer.parseInt(lst.get(1));

			MyDropboxSwing.cursor.setTid(Integer.parseInt(tid));
			MyDropboxSwing.cursor.setIndex(fileChangeId);

			client.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return 0;
		}
		return fileId;
	}
	public static int uploadDirectory(String directoryName,String tid) throws ClientProtocolException, IOException
	{

		String url = MyDropboxSwing.protocol+"://"+MyDropboxSwing.address+":"+MyDropboxSwing.port+"/user/"+MyDropboxSwing.userId+"/files/directory";
		//String url = "http://localhost:8112/user/1/files/directory";
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		StringEntity stringEntity = new StringEntity(directoryName);
		httpPost.setEntity(stringEntity);
		httpPost.addHeader("X-TID",tid);
		CloseableHttpResponse response = client.execute(httpPost);
		try{
			String fileId = EntityUtils.toString(response.getEntity());
			return Integer.parseInt(fileId);
		}catch(Exception ex)
		{
			return 0;
		}
	}
	public static int patchFile(int type,String tid, String oldName,String newName,Date timestamp)
	{
		XmlFactory fac = new XmlFactory();
		int fileId = fac.getFileIdByFileName(oldName);
		String url = MyDropboxSwing.protocol+"://"+MyDropboxSwing.address+":"+MyDropboxSwing.port+"/user/"+MyDropboxSwing.userId+"/file/"+fileId;
		HttpPatch httpPatch = new HttpPatch(url);
		DomRepresentation xml;
		try {
			xml = new DomRepresentation();
			xml.setIndenting(true);
			Document doc = xml.getDocument();

			Node userNode = doc.createElement("File");
			doc.appendChild(userNode);

			Node oldNameNode = doc.createElement("OldName");
			oldNameNode.setTextContent(oldName);
			userNode.appendChild(oldNameNode);


			Node newNameNode = doc.createElement("NewName");
			newNameNode.setTextContent(newName);
			userNode.appendChild(newNameNode);

			Node actionNode = doc.createElement("Type");
			actionNode.setTextContent(Integer.toString(type));
			userNode.appendChild(actionNode);

			Node tidNode = doc.createElement("TID");
			tidNode.setTextContent(tid);
			userNode.appendChild(tidNode);


			Node timestampNode = doc.createElement("NewName");
			timestampNode.setTextContent(timestamp.toString());
			userNode.appendChild(timestampNode);

			httpPatch.setEntity(new StringEntity(xml.toString(),ContentType.TEXT_XML));
			ClientResource cr = new ClientResource(url);
			cr.patch(xml);
			//CloseableHttpResponse response = client.execute(httpPatch);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
	public static void main(String [] args)
	{
		UploadService upload = new UploadService();
		//upload.uploadFile("1926896_1482959935303343_7941230303166801896_n.jpg","2");
		try {
			upload.uploadDirectory("as", "2");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
