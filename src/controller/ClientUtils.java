package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.UserDefinedFileAttributeView;

import model.Constants;

public class ClientUtils {
	public static void addFileAttribute(Path path, String key,String value)
	{
		final UserDefinedFileAttributeView view = Files.getFileAttributeView(path,UserDefinedFileAttributeView.class);
		try {
			final byte [] valueByte = value.getBytes("UTF-8");
			final ByteBuffer buffer = ByteBuffer.allocate(valueByte.length);
			buffer.put(valueByte);
			buffer.flip();
			view.write(key, buffer);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String getSyncAttribute(Path path)
	{
		
		try {
			final UserDefinedFileAttributeView view = Files.getFileAttributeView(path,UserDefinedFileAttributeView.class);
			//Read attribute
			final ByteBuffer readBuffer = ByteBuffer.allocate(view.size(Constants.SYNC_KEY));
			view.read(Constants.SYNC_KEY, readBuffer);
			readBuffer.flip();
			final String valueFromAttributes = new String(readBuffer.array(), "UTF-8");
			System.out.println("File Attribute: " + valueFromAttributes);
			return valueFromAttributes;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
}
