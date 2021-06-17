package ppppp.mq.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.stereotype.Service;
import ppppp.mq.bean.Goods;

@Service
@RabbitListener(queues = "queue-kk")
public class ReceiveController {

    @RabbitHandler
    public void receiveMQ(String msg) throws JsonProcessingException {
        //System.out.println("String ---  " + msg);
        ObjectMapper mapper=new ObjectMapper();
        Goods goods=mapper.readValue(msg,Goods.class);
        System.out.println("String ---  " + goods);
    }

    @RabbitHandler
    public void receiveMQ(byte[] msg){
        //System.out.println("byte[] ---  " + msg);
        Goods goods = (Goods) SerializationUtils.deserialize(msg);
        System.out.println("byte[] ---  " + goods);
    }


    @RabbitHandler
    public void receiveMQ(Goods goods){
        System.out.println("goods ---  " + goods);
    }
}
