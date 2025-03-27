// Un ejemplo más avanzado: Patrón de trabajo (Work Queues)
// Productor con Work Queue
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NuevoProductor {
    private final static String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // Declaramos una cola durable
            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

            // Mensaje que simula una tarea con puntos que representan complejidad
            String mensaje = "Tarea compleja...";

            // Publicamos con persistencia para garantizar que el mensaje no se pierda
            channel.basicPublish("", TASK_QUEUE_NAME,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    mensaje.getBytes());

            System.out.println(" [x] Enviado '" + mensaje + "'");
        }
    }
}