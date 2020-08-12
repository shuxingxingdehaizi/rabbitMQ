package org.ethan.test.rabbitMq;

import org.ethan.test.rabbitMq.consumer.BasicConsumer;
import org.ethan.test.rabbitMq.publisher.BasicPublisher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author chinacsci
 */
@Service
public class Test {

    public Test() throws IOException, InterruptedException {
        String queueName1 = "ethan.test.queue1";
        String queueName2 = "ethan.test.queue2";
        Thread consumer1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BasicConsumer.init("consumer1",queueName1);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
        });
        consumer1.setDaemon(true);
        consumer1.start();

        Thread consumer2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BasicConsumer.init("consumer2",queueName2);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
        });
        consumer2.setDaemon(true);
        consumer2.start();

        int index = 0;
        while (true){
            Thread.sleep(1000L);
            BasicPublisher.publish("Hello rabbit!"+index++);
        }


    }
}
