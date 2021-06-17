package ppppp.mq.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppppp.mq.bean.Goods;

@Service
public class MQService {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMsg(Goods goods) throws JsonProcessingException {
        //1.直接发送bean
        //amqpTemplate.convertAndSend("","queue-kk",goods);

        //2.发送byte
        //byte[] serializeGoods = SerializationUtils.serialize(goods);
        //amqpTemplate.convertAndSend("","queue-kk",serializeGoods);

        //3.以json进行传输
        ObjectMapper mapper=new ObjectMapper();
        String message=mapper.writeValueAsString(goods);
        amqpTemplate.convertAndSend("queue-kk",message);
    }
}
