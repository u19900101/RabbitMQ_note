package ppppp.mq.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ppppp.mq.bean.Goods;

@Service
@RabbitListener(queues = "queue-kk")
public class ReceiveController {

    @RabbitHandler
    public void receiveMQ(String msg){
        System.out.println("String ---  " + msg);
    }

    @RabbitHandler
    public void receiveMQ(byte[] msg){
        System.out.println("byte[] ---  " + msg);
    }

    @RabbitHandler
    public void receiveMQ(Goods goods){
        System.out.println("goods ---  " + goods);
    }
}
