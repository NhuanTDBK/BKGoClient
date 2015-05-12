package service;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HttpConnectionPool {
	private static CloseableHttpClient httpClient;
	static
	{
		try
		{
			PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
			cm.setMaxTotal(200);
			httpClient = HttpClients.custom().setConnectionManager(cm).build();

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public static CloseableHttpClient getClient()
	{
		return httpClient;
	}
}
