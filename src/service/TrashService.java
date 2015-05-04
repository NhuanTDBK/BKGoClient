/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import model.FileCursor;
import mydropbox.MyDropboxSwing;
import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;
import org.w3c.dom.Document;

/**
 *
 * @author nhuan
 */
public class TrashService {

    public DomRepresentation getTrash() {
        DomRepresentation dom = null;
        Representation response = null;
        try {
            dom = new DomRepresentation();
            Document doc;
            doc = dom.getDocument();
            String url = MyDropboxSwing.protocol + "://" + MyDropboxSwing.address + ":" + MyDropboxSwing.port + "/user/" + MyDropboxSwing.userId + "/trash";
            //String url = "http://localhost:8112/user/1/trash";
            ClientResource client = new ClientResource(url);
            response = client.get();
            if (response.getMediaType().equals(MediaType.TEXT_XML) || response.getMediaType().equals(MediaType.APPLICATION_ALL_XML)) {
                dom = new DomRepresentation(response);
            } else {
                return null;
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("Loi server " + e.getMessage());
            return null;
        }
        return dom;
    }
}
