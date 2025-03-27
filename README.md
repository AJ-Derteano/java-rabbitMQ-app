# Iniciar RabbitMQ
docker-compose up -d

El servidor RabbitMQ estará disponible en:

Puerto AMQP: 5672
Interfaz administrativa: 
http://localhost:15672 
(usuario: guest, contraseña: guest)

## 
# Compilar el proyecto
mvn clean package

# En una terminal, ejecuta el consumidor
java -cp target/rabbitmq-java-example-1.0-SNAPSHOT.jar Consumidor

# En otra terminal, ejecuta el productor
java -cp target/rabbitmq-java-example-1.0-SNAPSHOT.jar Productor

# verificar
http://localhost:15672
