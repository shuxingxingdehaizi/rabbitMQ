package org.ethan.test.rabbitMq.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author chinacsci
 */
public class BasicConsumer {

    static ConnectionFactory connectionFactory;

    private static Connection connection;

    private static Channel channel;

    private static QueueingConsumer consumer;

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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void init(String consumerName,String queueName) throws IOException, InterruptedException, TimeoutException {
        connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(queueName, true, false, false, null);
        QueueingConsumer consumer = new QueueingConsumer(channel);

        channel.basicConsume(queueName,true,consumer);
        System.out.println("Consumer["+consumerName+"] init complete");
        while(true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            // 获取消息正文
            String message = new String(delivery.getBody(),"UTF-8");
            System.out.println("!!!!!!!!!!!!!!!!["+consumerName+"]Received '" + message + "'");
        }
    }

}
