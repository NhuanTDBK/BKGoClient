package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import mydropbox.MyDropboxSwing;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DownloadService {

    public static CloseableHttpClient getHttpClient() {
        return HttpConnectionPool.getClient();
    }
    public Document getServer() {
        // TODO Auto-generated method stub
        String URL = MyDropboxSwing.protocol + "://" + MyDropboxSwing.address + ":" + MyDropboxSwing.port + "/user/" + MyDropboxSwing.userId + "/files/all";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(URL);
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpGet);

        } catch (ClientProtocolException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        HttpEntity entity = response.getEntity();
        Document doc = null;
        if (entity != null) {
            InputStream stream = null;
            try {
                stream = entity.getContent();
            } catch (IllegalStateException | IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = null;
            try {
                builder = builderFactory.newDocumentBuilder();
                File file = new File("_tmp.xml");
                OutputStream out = new FileOutputStream(file);
                IOUtils.copy(stream, out);
                out.close();
                doc = builder.parse(new FileInputStream("_tmp.xml"));
                client.close();
                response.close();
            } catch (SAXException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return doc;
    }
    /*
     * Download file theo id
     */

    public void downloadFile(String fileId, String name) {
       
    	String URL = MyDropboxSwing.protocol + "://" + MyDropboxSwing.address + ":" + MyDropboxSwing.port + "/user/" + MyDropboxSwing.userId + "/file/" + fileId;
        CloseableHttpClient client = getHttpClient();
        //String URL = "http://localhost:8112/user/1/file/"+fileId;
        HttpGet httpGet = new HttpGet(URL);
        CloseableHttpResponse response;
        final Long largeSize = 2L * FileUtils.ONE_GB;
        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();

            int status = response.getStatusLine().getStatusCode();
            if (status == 200) {
                String filePath = MyDropboxSwing.tmpFolder + "/" + name;
                Path path = Paths.get(filePath);
                File targetFile = path.toFile();
                InputStream stream = entity.getContent();
                OutputStream out = FileUtils.openOutputStream(targetFile);
                Date begin = new Date();
                System.out.println("Started: " + begin.getTime());
                if (entity.getContentLength() < largeSize) {
                    IOUtils.copy(stream, out);
                } else {
                    IOUtils.copyLarge(stream, out);
                }
                Date end = new Date();
                System.out.println("Ended: " + end.getTime());
                out.close();
                httpGet.releaseConnection();
                response.close();
            } else {
                System.out.println("File khong ton tai");
            }
            //client.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        DownloadService download = new DownloadService();
        download.downloadFile("117", "diff2");
    }

}
