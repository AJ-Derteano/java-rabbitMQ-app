
// Productor de mensajes
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Productor {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()) {

            // Declaramos la cola a la que enviaremos los mensajes
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // Mensaje que queremos enviar a la cola
            String mensaje = "Hola, Andrés desde => RabbitMQ!";

            // Publicamos el mensaje en la cola
            channel.basicPublish("", QUEUE_NAME, null, mensaje.getBytes());

            System.out.println(" [x] Enviado '" + mensaje + "'");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}