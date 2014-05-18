package Framework;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import PublicClasses.CommandObject;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class FrameMessSender {

    private final static String QUEUE_NAME = "hello";
    private ConnectionFactory _factory;

    public FrameMessSender() throws IOException {
        _factory = new ConnectionFactory();
        _factory.setHost("localhost");
    }

    public void sendMessage(CommandObject message) {
		//Scanner s = new Scanner(System.in);

        //ConnectionFactory factory = new ConnectionFactory();
        Connection connection;
        try {
            connection = _factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

			//ausgelagert
            //FrameMessReceiver frameMessRcvr = new FrameMessReceiver();
            //Thread t = new Thread(frameMessRcvr);
            //t.start();
            //sobald GUI vorhanden hier Parameter ändern
//            CommandObject commandObj = new CommandObject("john",
//                    "/home/rootkid/john-1.7.9-jumbo-7/run", message, true,
//                    true, "", "", 5);

            byte[] bytes;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(message);
                oos.flush();
                oos.reset();
                bytes = baos.toByteArray();
                oos.close();
                baos.close();
            } catch (IOException e) {
                bytes = new byte[]{};
                // Logger.getLogger("bsdlog").error("Unable to write to output stream",e);
            }

            channel.basicPublish("", QUEUE_NAME, null, bytes);
            System.out.println(" [x] Sent '" + message + "'---");

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

		// channel.close();
        // connection.close();
    }
	// public static void main(String[] argv) throws java.io.IOException {
    //
    // Scanner s = new Scanner(System.in);
    //
    // ConnectionFactory factory = new ConnectionFactory();
    // factory.setHost("localhost");
    // Connection connection = factory.newConnection();
    // Channel channel = connection.createChannel();
    //
    // channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    //
    // FrameMessReceiver frameMessRcvr = new FrameMessReceiver();
    // Thread t = new Thread(frameMessRcvr);
    // t.start();
    //
    // while(true){
    // System.out.print("?-");
    // String message = s.nextLine();
    // CommandObject commandObj = new
    // CommandObject("john","/home/rootkid/john-1.7.9-jumbo-7/run",
    // message, true, true, "", "", 5);
    //
    // byte[]bytes;
    // ByteArrayOutputStream baos = new ByteArrayOutputStream();
    // try{
    // ObjectOutputStream oos = new ObjectOutputStream(baos);
    // oos.writeObject(commandObj);
    // oos.flush();
    // oos.reset();
    // bytes = baos.toByteArray();
    // oos.close();
    // baos.close();
    // } catch(IOException e){
    // bytes = new byte[] {};
    // //Logger.getLogger("bsdlog").error("Unable to write to output stream",e);
    // }
    //
    // channel.basicPublish("", QUEUE_NAME, null, bytes);
    // System.out.println(" [x] Sent '" + message + "'");
    //
    // }
    //
    // //channel.close();
    // //connection.close();
    // }

}
