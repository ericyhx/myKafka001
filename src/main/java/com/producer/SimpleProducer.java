package com.producer;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

/**
 * 简单的生产者
 *
 */
@SuppressWarnings("deprecation")
public class SimpleProducer{
    private static Producer<Integer,String> producer;
    private static final Properties props=new Properties();

    public static void main(String[] args) {
        props.put("metadata.broker.list","localhost:9092");
        props.put("serializer.class","kafka.serializer.StringEncoder");
        //指定分区函数
        props.put("partitioner.class","com.producer.SimplePartitioner");

        props.put("request.required.acks","1");
        producer=new Producer<Integer, String>(new ProducerConfig(props));
        KeyedMessage<Integer,String> msg=new KeyedMessage<Integer, String>("test","hello world from win7 client");
        producer.send(msg);
        System.out.println("over");
    }
}
