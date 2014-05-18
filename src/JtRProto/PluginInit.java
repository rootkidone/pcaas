package JtRProto;

import java.io.IOException;

import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;

public class PluginInit {
	
	public static void main(String[] a) throws ShutdownSignalException, ConsumerCancelledException, IOException, InterruptedException{
		PlugInMessReceiver receiver = new PlugInMessReceiver();
		receiver.run();
	}

}
