package service;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import model.FileCursor;
import mydropbox.MyDropboxSwing;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.Header;
import org.apache.http.impl.client.CloseableHttpClient;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.ClientResource;
import org.w3c.dom.Document;

public class SyncService {
	public static final String url ="http://"+MyDropboxSwing.address+":"+MyDropboxSwing.port+"/user/"+MyDropboxSwing.userId+"/transaction";
	//public static final String url = "http://localhost:8112/user/1/transaction";
	
	private static CloseableHttpClient getHttpClient() {
		return HttpConnectionPool.getClient();
	}
	public static int getTransaction()
	{
		int tid = 0;
		ClientResource client = new ClientResource(url);
		try{
			DomRepresentation dom = new DomRepresentation(client.get());
			tid = Integer.parseInt(dom.getText("/Transaction/TID"));
		}
		catch(NumberFormatException ex)
		{
			System.out.println("Loi chuyen so");
		}
		return tid;
	}
	public static FileCursor getCursor()
	{
		int tid = 0;
		String url = MyDropboxSwing.protocol+"://"+MyDropboxSwing.address+":"+MyDropboxSwing.port+"/user/"+MyDropboxSwing.userId+"/transaction";
		ClientResource client = new ClientResource(url);
		FileCursor cursor = new FileCursor();
		int index = 0;
		try{
			DomRepresentation dom = new DomRepresentation(client.get());
			tid = Integer.parseInt(dom.getText("/Transaction/TID"));cursor.setTid(tid);
			index = Integer.parseInt(dom.getText("/Transaction/Index"));cursor.setIndex(index);
		}
		catch(NumberFormatException ex)
		{
			System.out.println("Loi chuyen so");
		}
		return cursor;
	}
	public DomRepresentation getLatestUpdate(FileCursor cursor)
	{
		CloseableHttpClient client = getHttpClient();
		HttpPost httpPost = new HttpPost(url);
		DomRepresentation dom = null;
		String str = cursor.toString();
		try {
			StringEntity entity = new StringEntity(str);
			httpPost.setEntity(entity);
			CloseableHttpResponse response = client.execute(httpPost);
			HttpEntity res = response.getEntity();	
			ContentType contentType = ContentType.get(res);
			if(contentType.getMimeType().equals(ContentType.TEXT_XML.getMimeType()))
			{
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = factory.newDocumentBuilder();
				Document doc = docBuilder.parse(res.getContent());
				dom = new DomRepresentation();
				dom.setDocument(doc);
			}
			httpPost.releaseConnection();
			response.close();
		} catch (Exception ex) {

		}
		return dom;	
	}
	public static void main(String [] args)
	{

		SyncService service = new SyncService();
		DomRepresentation dom = service.getLatestUpdate(new FileCursor("0","0"));
		try {
			System.out.println(dom==null?"null":"co du lieu \n " + dom.getText());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
