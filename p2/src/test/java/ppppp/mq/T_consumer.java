package ppppp.mq;

import com.rabbitmq.client.*;

import java.io.IOException;

import static ppppp.mq.P2ApplicationTests.getConnection;

public class T_consumer {
    public static void main(String[] args) throws Exception {
        //创建一个新的连接
        Connection connection = getConnection();
        //创建一个通道
        Channel channel = connection.createChannel();

        // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
        Consumer consumer = new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Customer Received '" + message + "'");
            }
        };
        //自动回复队列应答 -- RabbitMQ中的消息确认机制
        channel.basicConsume("queue_delay2", true, consumer);
    }

}
