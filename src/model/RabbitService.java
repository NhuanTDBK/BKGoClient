package model;

import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

public class RabbitService extends EndPoint implements Consumer{

	public RabbitService(String endPointName, String queueName) {
		super(endPointName, queueName);
	}
	public void sendMessage(Serializable object) throws IOException {
	    channel.basicPublish("",this.getQueueName(), null, SerializationUtils.serialize(object));
	}	
	public void sendMessage(String object) throws IOException {
	    channel.basicPublish("",this.getQueueName(), null, object.getBytes());
	}	
	public void sendMessageToExchange(String object)
	{
		try {
			channel.basicPublish(this.getExchangeName(),"", null, object.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void sendMessageToExchange(Serializable object)
	{
		try {
			channel.basicPublish(this.getExchangeName(),"", null, SerializationUtils.serialize(object));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void handleCancel(String arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void handleCancelOk(String arg0) {
		// TODO Auto-generated method stub
		System.out.println("Consumer is cancel "+arg0);
	}
	@Override
	public void handleConsumeOk(String arg0) {
		// TODO Auto-generated method stub
		System.out.println("Consumer is handle "+arg0);
	}
	@Override
	public void handleDelivery(String consumeTag, Envelope env,
			BasicProperties props, byte[] body) throws IOException {
		// TODO Auto-generated method stub
		FileChange fileChange = (FileChange)SerializationUtils.deserialize(body);
		System.out.println("Consumer "+ fileChange.getFileName());
	}
	@Override
	public void handleRecoverOk(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void handleShutdownSignal(String arg0, ShutdownSignalException arg1) {
		// TODO Auto-generated method stub
		
	}
}
