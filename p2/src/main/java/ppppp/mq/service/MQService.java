package ppppp.mq.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppppp.mq.bean.Goods;

@Service
public class MQService {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMsg(Goods goods){
        amqpTemplate.convertAndSend("","queue-kk",goods);
    }
}
