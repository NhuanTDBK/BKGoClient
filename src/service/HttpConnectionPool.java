package service;

import java.util.concurrent.TimeUnit;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.IdleConnectionEvictor;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HttpConnectionPool {
	private static CloseableHttpClient httpClient;
	static
	{
		try
		{
			IdleConnectionEvictor monitor;
			PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
			cm.setMaxTotal(200);
			httpClient = HttpClients.custom().setConnectionManager(cm).build();
			monitor = new IdleConnectionEvictor(cm, 20000,TimeUnit.MILLISECONDS);
			monitor.start();
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
