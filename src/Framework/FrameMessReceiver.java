package Framework;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import JtRProto.ProcessControl;
import PublicClasses.CommandObject;
import PublicClasses.RegistryMessage;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class FrameMessReceiver implements Runnable {

    private FrameworkController _frameworkController;

    public FrameMessReceiver(FrameworkController fc) {
        this._frameworkController = fc;
    }

    public void receiveMessage() throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
        String QUEUE_NAME = "hello2";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, true, consumer);

        while (true) {

            //auf NachrichtenObjekt prüfen mit controller
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();

            Object receivedObj = null;
            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(
                        delivery.getBody());
                ObjectInputStream ois = new ObjectInputStream(bis);

                receivedObj = ois.readObject();
                if (receivedObj instanceof CommandObject) {
                    //in der GUI anzeigen lassen
                    CommandObject commandObj = (CommandObject) receivedObj;
                    String message = new String(commandObj.getCommand());
                    System.out.println(" [x] Received '" + commandObj.getCommand() + "'");
                    _frameworkController.showMessageInGUI(commandObj.getCommand(),
                            commandObj.getProgramInstance());
                } //registry nachricht
                else {
                    //registryObject.getPlugin
                    RegistryMessage regMess = (RegistryMessage) receivedObj;
                    _frameworkController.addPluginToManager(regMess.getPlugin());
                    System.out.println(" [x] Received '" + regMess.getPlugin() + "-Plugin'");
                }
                ois.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            receiveMessage();
        } catch (ShutdownSignalException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ConsumerCancelledException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
