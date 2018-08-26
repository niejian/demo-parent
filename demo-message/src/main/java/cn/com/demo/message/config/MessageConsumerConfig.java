//package cn.com.demo.message.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.Consumer;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author: code4fun
// * @date: 2018/8/23:上午11:07
// */
//@Slf4j
//@Configuration
//public class MessageConsumerConfig {
//    /**
//     * consumer:
//     *       enable-auto-commit: true
//     *       auto-commit-interval: 1000
//     *       key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
//     *       value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
//     *       group-id: demo_message
//     */
//    @Value("${spring.kafka.bootstrap-servers}")
//    private String servers;
//    @Value("${spring.kafka.consumer.auto-commit-interval}")
//    private int interval;
//    @Value("${spring.kafka.consumer.enable-auto-commit}")
//    private boolean autoCommit;
//    @Value("${spring.kafka.consumer.group-id}")
//    private String groupId;
//    @Value("${spring.kafka.consumer.key-deserializer}")
//    private String keyDeserializer;
//    @Value("${spring.kafka.consumer.value-deserializer}")
//    private String valueDeserializer;
//
//    @Bean
//    public Map<String, Object> consumerConfigs() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
//        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, autoCommit);
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
//        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, interval);
//
//        return props;
//    }
//
//    @Bean
//    public ConsumerFactory<Integer, String> consumerFactory() {
//        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
//    }
//
//    @Bean
//    ConcurrentKafkaListenerContainerFactory<Integer, String>
//    kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<Integer, String> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
//}
