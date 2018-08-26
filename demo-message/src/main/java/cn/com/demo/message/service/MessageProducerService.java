package cn.com.demo.message.service;

/**
 * @author: code4fun
 * @date: 2018/8/23:上午9:24
 */
public interface MessageProducerService {
    void sendMessage(String messageTopic, String message) throws Exception;
}
