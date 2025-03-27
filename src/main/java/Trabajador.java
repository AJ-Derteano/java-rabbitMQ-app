// Trabajador (consumidor) para Work Queue
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Trabajador {
    private final static String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // Declaramos la misma cola durable
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Esperando mensajes. Para salir presiona CTRL+C");

        // Solo procesa un mensaje a la vez
        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String mensaje = new String(delivery.getBody(), "UTF-8");

            System.out.println(" [x] Recibido '" + mensaje + "'");

            try {
                // Simulamos el procesamiento del trabajo
                doWork(mensaje);
            } finally {
                System.out.println(" [x] Hecho");
                // Confirmación manual del mensaje
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };

        // Configuramos consumo con confirmación manual (false)
        channel.basicConsume(TASK_QUEUE_NAME, false, deliverCallback, consumerTag -> { });
    }

    private static void doWork(String task) {
        try {
            // Simula trabajo: cada punto en el mensaje representa un segundo de trabajo
            for (char ch : task.toCharArray()) {
                if (ch == '.') {
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}