package ppppp.consumer.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = "queue-kk")
public class ReceiveMsg {
    @RabbitHandler
    public void receiveMsg(String msg){
        System.out.println("接收到的消息为 ："+msg);
    }
}
