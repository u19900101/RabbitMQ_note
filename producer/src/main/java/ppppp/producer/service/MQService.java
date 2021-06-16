package ppppp.producer.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MQService {

    @Resource
    private  AmqpTemplate amqpTemplate;
    public void sendMsg(String msg) {
        if(msg.startsWith("q_")){
            //1. 发送消息到队列
            amqpTemplate.convertAndSend("queue-kk",msg);
        }else if(msg.startsWith("f_")){
            //2. 发送消息到订阅交换机
            amqpTemplate.convertAndSend("ex1","",msg);
        }else if(msg.startsWith("r_")){
            //3. 发送消息到路由交换机
            if(msg.startsWith("r_a")){
                amqpTemplate.convertAndSend("ex2","a",msg);
            }else if(msg.startsWith("r_b")){
                amqpTemplate.convertAndSend("ex2","b",msg);
            }
        }
    }
}
