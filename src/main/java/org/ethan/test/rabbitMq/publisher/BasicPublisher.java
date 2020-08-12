package org.ethan.test.rabbitMq.publisher;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author chinacsci
 */
public class BasicPublisher {

    static ConnectionFactory connectionFactory;

    private static Connection connection;

    private static Channel channel;

    private static final String QUEUE_NAME = "ethan.test.queue";

    private static final String ROUTING_KEY = "ethan.test.routeKey";

    private static final String EXCHANGE = "ethanDelayExchange";

    static{
        try {
            connectionFactory = new ConnectionFactory();

            connection = connectionFactory.newConnection();

            connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("127.0.0.1");
            connectionFactory.setPort(5672);
            connectionFactory.setVirtualHost("/");
            connectionFactory.setUsername("guest");
            connectionFactory.setPassword("guest");

            connection = connectionFactory.newConnection();

            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void publish(String msg) throws IOException {
        AMQP.BasicProperties properties = new AMQP.BasicProperties();
        Map<String, Object> headers = new HashMap<>();
        headers.put("x-delay", "6000");
        properties.builder().headers(headers);

        channel.basicPublish(EXCHANGE,ROUTING_KEY,false,false,properties,msg.getBytes("UTF-8"));
        System.out.println("Publish msg:"+msg);
    }

}
