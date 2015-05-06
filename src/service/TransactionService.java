package service;

import model.FileCursor;
import mydropbox.MyDropboxSwing;

import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;

public class TransactionService {
	public static final String url = MyDropboxSwing.protocol+"://"+MyDropboxSwing.address+":"+MyDropboxSwing.port+"/user/"+MyDropboxSwing.userId+"/transaction";
	public static ClientResource clientResource = new ClientResource(url);
	public static ClientResource getClientResource()
	{
		return clientResource;
	}
	public static int getTransaction()
	{
		int tid = 0;
		String url = MyDropboxSwing.protocol+"://"+MyDropboxSwing.address+":"+MyDropboxSwing.port+"/user/"+MyDropboxSwing.userId+"/transaction";
		ClientResource client = new ClientResource(url);
		try{
			DomRepresentation dom = new DomRepresentation(client.get());
			tid = Integer.parseInt(dom.getText("/Transaction/TID"));
			client.release();
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
		DomRepresentation dom = null;
		Representation response = null;
		try {
			dom= new DomRepresentation();
			//String url = MyDropboxSwing.protocol+"://"+MyDropboxSwing.address+":"+MyDropboxSwing.port+"/user/"+MyDropboxSwing.userId+"/transaction";
			StringRepresentation request = new StringRepresentation(cursor.toString());
			ClientResource client = getClientResource();
			response = client.post(request);	
			if(response.getMediaType().equals(MediaType.TEXT_XML)||response.getMediaType().equals(MediaType.APPLICATION_ALL_XML))
				dom = new DomRepresentation(response);
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
//		System.out.println(http.getLatestUpdate(new FileCursor("0", "0"))==null?"null":"have");
//	}
}
