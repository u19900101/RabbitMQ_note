package ppppp.mq;

import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class P2ApplicationTests {

   @Test
   public void T_M() throws Exception {
       Connection connection = getConnection();
       Channel channel = connection.createChannel();
       String message = "Hello World!";

        //1.开启消息确认
       channel.confirmSelect();
        //2.发送消息
       channel.basicPublish("ex2", "c", null, message.getBytes());
        //3.获取确认
       boolean b = channel.waitForConfirms();
       System.out.println("消息发送"+(b?"成功":"失败"));

       channel.close();
       connection.close();
   }

   @Test
   public void T_异步批量发送消息() throws Exception {
       Connection connection = getConnection();
       Channel channel = connection.createChannel();
       String message = "Hello World!";

        //1.开启消息确认
       channel.confirmSelect();

       //3.开启异步confirm
       channel.addConfirmListener(new ConfirmListener() {
           //参数l表示返回的消息标识，
           //参数b表示是否为批量confirm
           public void handleAck(long l, boolean b) throws IOException {
               System.out.println("----消息发送成功");
           }
           public void handleNack(long l, boolean b) throws IOException {
               System.out.println("----消息发送失败");
           }
       });

       //4.添加return监控
       channel.addReturnListener(new ReturnListener() {
           //消息未分发到队列中时，会执行此语句
           @Override
           public void handleReturn(int replyCode,
                                    String replyText,
                                    String exchange,
                                    String routingKey,
                                    AMQP.BasicProperties properties, byte[] body) throws IOException {
               System.out.println("发送失败*****");
               System.out.println("replyCode————"+replyCode);
               System.out.println("replyText————"+replyText);
               System.out.println("exchange————"+exchange);
               System.out.println("routingKey————"+routingKey);
               System.out.println("body————"+new String(body));
           }
       });

        //2.发送消息
       for (int i=1; i<=10; i++) {
           message += i;
           //channel.basicPublish("ex2", "c", null, message.getBytes());
           channel.basicPublish("ex2", "c",true, null, message.getBytes());
       }
       //channel.close();
       //connection.close();
   }


   @Test
   public void T_消息的return机制() throws Exception {
       //创建一个新的连接
       Connection connection = getConnection();
       //创建一个通道
       Channel channel = connection.createChannel();

       //声明要关注的队列
       //channel.queueDeclare("queue1", false, false, false, null);
       //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
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
       channel.basicConsume("queue3", true, consumer);

   }
    @Test
    void contextLoads() throws Exception {
        // 获取到连接以及mq通道
        Connection connection = getConnection();
        // 相当于数据库中的创建连接
        // 从连接中创建通道
        Channel channel = connection.createChannel();
        //1. 新建队列
        //参数1：queue - 指定队列的名称
        //参数2：durable - 当前队列是否需要持久化（true）
        //参数3：exclusive - 是否排外（conn.close() - 当前队列会被自动删除，当前队列只能被一个消费者消费）
        //参数4：autoDelete - 如果这个队列没有消费者在消费，队列自动删除
        //参数5：arguments - 指定当前队列的其他信息
        channel.queueDeclare("queue1",true,false,false,null);
        channel.queueDeclare("queue2",true,false,false,null);
        //2. 创建exchange
        //参数1： exchange的名称
        //参数2： 指定exchange的类型  FANOUT - pubsub ,   DIRECT - Routing , TOPIC - Topics
        channel.exchangeDeclare("ex3", BuiltinExchangeType.FANOUT);
        channel.exchangeDeclare("ex4", BuiltinExchangeType.DIRECT);

        //3.绑定某一个队列到交换机
        channel.queueBind("queue1","ex3","");
        channel.queueBind("queue1","ex4","r1");
        channel.queueBind("queue2","ex4","r2");
    }

    public static Connection getConnection() throws Exception {
        //定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置服务地址
        factory.setHost("127.0.0.1");
        //端口
        factory.setPort(5672);
        // 设置账号信息，用户名、密码、vhost
        factory.setVirtualHost("host1");
        factory.setUsername("kk");
        factory.setPassword("kk");
        // 通过工程获取连接
        Connection connection = factory.newConnection();
        return connection;
    }

}
