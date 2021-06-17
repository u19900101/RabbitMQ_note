package ppppp.mq.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class MsgConfirm_Return implements RabbitTemplate.ConfirmCallback,
        RabbitTemplate.ReturnCallback {

    Logger logger = LoggerFactory.getLogger(MsgConfirm_Return.class);

    @Resource
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void initMethod(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        //此方法用于监听消息认结果(消息是否发述到交换机
        if(ack){
            logger.info("***消息成功发送到交换机");
        }else {
            logger.info("---消息 发送到交换机 失败");
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.warn("---消息发送到队列失败");
    }
}
