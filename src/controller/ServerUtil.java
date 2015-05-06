package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import model.Constants;

public class ServerUtil {
	public static String encryptMessage(String input,String algorithm) throws NoSuchAlgorithmException
	{
		MessageDigest md = MessageDigest.getInstance(algorithm);
		md.update(input.getBytes());
		byte[] output = md.digest();
		return bytesToHex(output);
	}
	public static String encryptFile(FileInputStream input) throws NoSuchAlgorithmException, IOException
	{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte [] dataToRead = new byte [1024];
		int nRead = 0;
		while((nRead=input.read(dataToRead))!=-1)
		{
			md.update(dataToRead,0,nRead);
		}
		byte[] output = md.digest();
		return bytesToHex(output);
	}
	public static String bytesToHex(byte[] b) {

	      StringBuffer buf = new StringBuffer();
	      for (int j=0; j<b.length; j++) {
	       buf.append(Integer.toHexString(0xFF&b[j]));
	      }
	      return buf.toString();
	 }
	public static String convertPath(String fileFullPath, String fileParent)
	{
		Path fullPath = Paths.get(fileFullPath);
		Path fileP = Paths.get(fileParent);
		Path convert1 = fileP.relativize(fullPath);
		return convert1.toString();
	}
	public static ArrayList<String>getToken(String element)
	{
		ArrayList<String> lstToken = new ArrayList<String>();
		StringTokenizer token = new StringTokenizer(element, Constants.DELIMITER);
		while(token.hasMoreTokens())
		{
			lstToken.add(token.nextToken());
		}
		return lstToken;
	}
}
