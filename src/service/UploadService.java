package service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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
import org.apache.http.util.EntityUtils;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.ClientResource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class UploadService {

    private static CloseableHttpClient getHttpClient() {
        return HttpConnectionPool.getClient();
    }

    public static int uploadFile(String fileName, String tid) {
        String url = MyDropboxSwing.protocol + "://" + MyDropboxSwing.address + ":" + MyDropboxSwing.port + "/user/" + MyDropboxSwing.userId + "/files/file";
        
        File file = new File(MyDropboxSwing.urls + "/" + fileName);
        
        CloseableHttpClient client = getHttpClient();
        HttpPost httpPost = new HttpPost(url);
        int fileId = 0;
        int fileChangeId = 0;
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        try {
            builder.addBinaryBody("file", file, ContentType.create(Files.probeContentType(Paths.get(file.getPath()))), fileName);
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            httpPost.addHeader("X-TID", tid);
            //Thuc hien upload
            CloseableHttpResponse response = client.execute(httpPost);
            HttpEntity res = response.getEntity();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.parse(res.getContent());
            DomRepresentation dom = new DomRepresentation();
            dom.setDocument(doc);

            fileId = Integer.parseInt(dom.getText("/File/FileId"));
            fileChangeId = Integer.parseInt(dom.getText("/File/FileChangeId"));

            MyDropboxSwing.cursor.setTid(Integer.parseInt(tid));
            MyDropboxSwing.cursor.setIndex(fileChangeId);

            httpPost.releaseConnection();
            response.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
        return fileId;
    }

    public static int uploadDirectory(String directoryName, String tid) throws ClientProtocolException, IOException {

        String url = MyDropboxSwing.protocol + "://" + MyDropboxSwing.address + ":" + MyDropboxSwing.port + "/user/" + MyDropboxSwing.userId + "/files/directory";
        //String url = "http://localhost:8112/user/1/files/directory";
        CloseableHttpClient client = getHttpClient();
        HttpPost httpPost = new HttpPost(url);
        StringEntity stringEntity = new StringEntity(directoryName);
        httpPost.setEntity(stringEntity);
        httpPost.addHeader("X-TID", tid);
        CloseableHttpResponse response = client.execute(httpPost);
        try {
            String fileId = EntityUtils.toString(response.getEntity());
            httpPost.releaseConnection();
            response.close();
            return Integer.parseInt(fileId);
        } catch (Exception ex) {
            return 0;
        }
    }
}
