package JtRProto;

import PublicClasses.CommandObject;
import PublicClasses.PluginObject;
import PublicClasses.RegistryMessage;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

//Empfängt Nachrichten des Frameworks und reicht Diese weiter an
//ProcessControl
public class PlugInMessReceiver {

	private final static String QUEUE_NAME = "hello";

	public PlugInMessReceiver() {

	}

	public void run() throws IOException, ShutdownSignalException,
			ConsumerCancelledException, InterruptedException {
            
                //kann ausgelagert werden
                PluginMessSender pluginMessSender = new PluginMessSender();
                PluginObject po = new PluginObject("John the Ripper", new HashMap<String,String>());
                pluginMessSender.sendRegistryMessage(po);
                
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(QUEUE_NAME, true, consumer);

		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();

			CommandObject commandObj = null;
			try {
				ByteArrayInputStream bis = new ByteArrayInputStream(
						delivery.getBody());
				ObjectInputStream ois = new ObjectInputStream(bis);
				commandObj = (CommandObject) ois.readObject();
				ois.close();
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			}
			// String message = new String(delivery.getBody());
			System.out.println(commandObj.getProgramInstance());
			if (commandObj.getProgramInstance().equals("John the Ripper")) {
				System.out.println(" [x] Received '" + commandObj.getCommand()
						+ "'");
				ProcessControl pc = ProcessControl.create(commandObj
						.getCommand(), commandObj.getDirectory(), commandObj.getInterval(),
                                                pluginMessSender);
			} else {
				//objekt gehört zu anderem Plugin
			}
		}
	}

	// private final static String QUEUE_NAME = "hello";
	//
	// public static void main(String[] argv) throws Exception {
	//
	// ConnectionFactory factory = new ConnectionFactory();
	// factory.setHost("localhost");
	// Connection connection = factory.newConnection();
	// Channel channel = connection.createChannel();
	//
	// channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	// System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
	//
	// QueueingConsumer consumer = new QueueingConsumer(channel);
	// channel.basicConsume(QUEUE_NAME, true, consumer);
	//
	// while (true) {
	// QueueingConsumer.Delivery delivery = consumer.nextDelivery();
	// String message = new String(delivery.getBody());
	// System.out.println(" [x] Received '" + message + "'");
	// ProcessControl pc = ProcessControl.create(message);
	// }
	// }
}
