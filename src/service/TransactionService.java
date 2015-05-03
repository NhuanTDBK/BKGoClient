package service;

import java.io.IOException;

import model.FileCursor;
import mydropbox.MyDropboxSwing;

import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class TransactionService {
	public static int getTransaction()
	{
		int tid = 0;
		String url = MyDropboxSwing.protocol+"://"+MyDropboxSwing.address+":"+MyDropboxSwing.port+"/user/"+MyDropboxSwing.userId+"/transaction";
		ClientResource client = new ClientResource(url);
		int index = 0;
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
	public DomRepresentation getSync(String tid)
	{
		DomRepresentation dom=null;
		String url = MyDropboxSwing.protocol+"://"+MyDropboxSwing.address+":"+MyDropboxSwing.port+"/user/"+MyDropboxSwing.userId+"/sync/0";
		ClientResource client = new ClientResource(url);
		try{
			dom = new DomRepresentation(client.get());
			Document doc = dom.getDocument();
		}
		catch(ResourceException ex)
		{
			System.out.println("Loi chuyen so");
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dom;
	}
	public DomRepresentation getLatestUpdate(FileCursor cursor)
	{
		DomRepresentation dom = null;
		Representation response = null;
		try {
			dom= new DomRepresentation();
			Document doc;
			doc = dom.getDocument();
			String url = MyDropboxSwing.protocol+"://"+MyDropboxSwing.address+":"+MyDropboxSwing.port+"/user/"+MyDropboxSwing.userId+"/transaction";
			StringRepresentation request = new StringRepresentation(cursor.toString());
			ClientResource client = new ClientResource(url);
			response =client.post(request);	
			if(response.getMediaType().equals(MediaType.TEXT_XML)||response.getMediaType().equals(MediaType.APPLICATION_ALL_XML))
				dom = new DomRepresentation(response);
			else return null;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Loi server "+e.getMessage());
			return null;
		}
		return dom;
	}
//	public static void main(String [] args)
//	{
//		TransactionService http = new TransactionService();
//		http.getUpdate(new FileCursor("0", "0"));
//	}
}
