// Consumidor de mensajes
import com.rabbitmq.client.*;

public class Consumidor {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // Declaramos la misma cola
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Esperando mensajes. Para salir presiona CTRL+C");

        // Callback que se ejecutará cuando se reciba un mensaje
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String mensaje = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Recibido '" + mensaje + "'");
        };

        // Consumimos mensajes de la cola
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}