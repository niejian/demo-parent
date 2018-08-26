package cn.com.demo.message.service.impl;

import cn.com.demo.message.service.MessageConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author: code4fun
 * @date: 2018/8/23:上午10:06
 */
@Slf4j
@Service(value = "messageConsumerService")
public class MessageConsumerServiceimpl implements MessageConsumerService {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    static final String TOPIC = "topic_id_10_mssage";
    /**
     * 消费者接受消息
     *
     * @param topic
     * @return
     * @throws Exception
     */
    @Override
    public String getMessage(String topic) throws Exception {
        //kafkaTemplate.
        return null;
    }

    @KafkaListener(topics = TOPIC, groupId="demo-message")
    public String messageListener(ConsumerRecord consumerRecord) {
        String message = (String) consumerRecord.value();
        String topic = consumerRecord.topic();
        int partition = consumerRecord.partition();
        log.info("接受消息---> topic-->{},  消息内容---> {}, 分区：{} ", topic, message, partition);

        return message;
    }

//    @KafkaListener(topics = TOPIC)
//    public String messageListener2(ConsumerRecord consumerRecord) {
//        String message = (String) consumerRecord.value();
//        String topic = consumerRecord.topic();
//        int partition = consumerRecord.partition();
//        log.info("接受消息2---> topic-->{},  消息内容---> {}, 分区：{} ", topic, message, partition);
//
//        return message;
//    }
}
