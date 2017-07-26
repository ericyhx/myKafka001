package com;


import com.consumer.SimpleConsumer;
import com.producer.SimpleProducer1;

/**
 * Created by dasun on 2017/7/25.
 */
public class kafkaStart {
    public static void main(String[] args) {
        SimpleProducer1.startProducer();
        SimpleConsumer consumer=new SimpleConsumer("test");
        consumer.start();
    }
}
