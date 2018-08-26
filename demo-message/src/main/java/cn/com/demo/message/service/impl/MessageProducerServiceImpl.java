package cn.com.demo.message.service.impl;

import cn.com.demo.message.service.MessageProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author: code4fun
 * @date: 2018/8/23:上午9:25
 */
@Service(value = "messageProducerService")
@Slf4j
public class MessageProducerServiceImpl implements MessageProducerService {
    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Override
    public void sendMessage(String messageTopic, String message) throws Exception {
        log.info("发送消息-->topic:{}, message:{}",messageTopic, message);
        kafkaTemplate.send(messageTopic, message);

    }
}
