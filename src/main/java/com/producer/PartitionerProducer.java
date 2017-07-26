package com.producer;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

/**
 * Created by dasun on 2017/7/24.
 * 带有分区函数的生产者
 */
public class PartitionerProducer {
    public static void main(String[] args) {
        Producer<String,String> producer;
        Properties props=new Properties();
        props.put("metadata.broker.list","localhost:9092,localhost:9093");
        props.put("serializer.class","kafka.serializer.StringEncoder");
        //指定分区函数
        props.put("partitioner.class","com.producer.SimplePartitioner");
        props.put("request.required.acks","1");
        ProducerConfig config=new ProducerConfig(props);
        producer=new Producer<String, String>(config);

        for (int i = 0; i <100; i++) {
            KeyedMessage<String,String> message=new KeyedMessage<String, String>("test",i+"","how are you-"+i);
            producer.send(message);
        }
        System.out.println("over");
    }
}
