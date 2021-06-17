package ppppp.mq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class P2ApplicationTests {


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
