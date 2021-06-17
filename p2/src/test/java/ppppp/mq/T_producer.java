package ppppp.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.util.Scanner;

import static ppppp.mq.P2ApplicationTests.getConnection;

public class T_producer {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String  msg = null;
        while (!"q".equalsIgnoreCase(msg=scanner.nextLine())){
            Connection connection = getConnection();
            Channel channel = connection.createChannel();
            String message = "Hello World ————" + msg;

            channel.basicPublish("delay_exchange", "k1", null, message.getBytes());
            channel.close();
            connection.close();
        }
    }

}
