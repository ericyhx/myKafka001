package com.consumer;


import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.*;

/**
 * Created by dasun on 2017/7/25.
 */
public class SimpleConsumer extends Thread{
    private String topic;

    public SimpleConsumer(String topic){
        super();
        this.topic = topic;
    }
    @Override
    public void run() {
        System.out.println("consumer start....");
        //创建连接器
        ConsumerConnector consumer = createConsumer();
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, 1); // 一次从主题中获取一个数据
        Map<String, List<KafkaStream<byte[], byte[]>>>  messageStreams = consumer.createMessageStreams(topicCountMap);
        KafkaStream<byte[], byte[]> stream = messageStreams.get(topic).get(0);// 获取每次接收到的这个数据
        ConsumerIterator<byte[], byte[]> iterator =  stream.iterator();
        while(iterator.hasNext()){
            String message = new String(iterator.next().message());
            System.out.println("接收到: " + message);
        }
    }

    private ConsumerConnector createConsumer() {
        Properties properties = new Properties();
//        properties.put("zookeeper.connect", "192.168.1.110:2181,192.168.1.111:2181,192.168.1.112:2181");//声明zk
        properties.put("zookeeper.connect", "localhost:2181");//声明zk
        properties.put("group.id", "group1");// 必须要使用别的组名称， 如果生产者和消费者都在同一组，则不能访问同一组内的topic数据
        properties.put("zookeeper.session.timeout.ms","500");
        properties.put("zookeeper.sync.time.ms","250");
        properties.put("auto.commit.interval.ms","1000");
        //设置读取偏移量
        properties.put("auto.offset.reset","smallest");
        //创建消费者配置对象
        ConsumerConfig config=new ConsumerConfig(properties);
//        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        return Consumer.createJavaConsumerConnector(config);
    }


    public static void main(String[] args) {
        new SimpleConsumer("test").start();// 使用kafka集群中创建好的主题 test

    }
}
