package cn.com.demo.message.service;

/**
 * 消费者接受消息
 * @author: code4fun
 * @date: 2018/8/23:上午10:05
 */
public interface MessageConsumerService {
    /**
     * 消费者接受消息
     * @param topic
     * @return
     * @throws Exception
     */
    String getMessage(String topic) throws Exception;
}
