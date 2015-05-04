package service;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import mydropbox.MyDropboxSwing;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class VersionService {
	public static DomRepresentation getVersions()
	{
		return getVersion("root");
	}
	public static DomRepresentation getVersion(String fileName)
	{
		String url = MyDropboxSwing.protocol+"://"+MyDropboxSwing.address+":"+MyDropboxSwing.port+"/user/"+MyDropboxSwing.userId+"/revisions";
		//String url = "http://localhost:8112/user/1/files/directory";
		CloseableHttpClient client = HttpClients.createDefault();
		DomRepresentation dom = null;
		HttpGet httpPost = new HttpGet(url);
		httpPost.addHeader("X-PATH",fileName);
		CloseableHttpResponse response;
		try {
			response = client.execute(httpPost);
			dom = new DomRepresentation();
			HttpEntity res = response.getEntity();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			Document doc = docBuilder.parse(res.getContent());
			dom.setDocument(doc);
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dom;
	}
}
