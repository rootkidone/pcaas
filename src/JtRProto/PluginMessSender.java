package JtRProto;

import PublicClasses.CommandObject;
import PublicClasses.PluginObject;
import PublicClasses.RegistryMessage;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class PluginMessSender {

    private final String QUEUE_NAME = "hello2";
    private ConnectionFactory _factory;
    private Connection _connection;
    private Channel _channel;

    public PluginMessSender() throws IOException {
        _factory = new ConnectionFactory();
        _factory.setHost("localhost");
        _connection = _factory.newConnection();
        _channel = _connection.createChannel();
        _channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    }

    public void sendRegistryMessage(PluginObject po) throws IOException {
        RegistryMessage regMess = new RegistryMessage(po);
        byte[] bytes;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(regMess);
            oos.flush();
            oos.reset();
            bytes = baos.toByteArray();
            oos.close();
            baos.close();
        } catch (IOException e) {
            bytes = new byte[]{};
            //Logger.getLogger("bsdlog").error("Unable to write to output stream",e); 
        }
        _channel.basicPublish("", QUEUE_NAME, null, bytes);
        System.out.println(" [x] Sent '" + po.toString() + "'");
    }

    public void sendMessage(String message) throws IOException {
        //nicht alle parameter sind nötig für eine antwort
        CommandObject commandObj = new CommandObject("John the Ripper", "", message, true, true, "", "", 5);
        byte[] bytes;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(commandObj);
            oos.flush();
            oos.reset();
            bytes = baos.toByteArray();
            oos.close();
            baos.close();
        } catch (IOException e) {
            bytes = new byte[]{};
            //Logger.getLogger("bsdlog").error("Unable to write to output stream",e); 
        }
        _channel.basicPublish("", QUEUE_NAME, null, bytes);
        System.out.println(" [x] Sent '" + message + "'");
    }

}
