package com.producer;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.concurrent.Executors;

/**
 * 简单的生产者
 * 生产者是线程安全的，维护了本地buffer pool，发送消息，消息进入pool，
 * send方法异步，即刻返回
 *
 */
public class SimpleProducer1 {
//    public static void main(String[] args) {
    public static void startProducer(){
        Executors.newSingleThreadExecutor().execute(()->{
            Properties props=new Properties();
            props.put("bootstrap.servers","localhost:9092");
            props.put("acks","all");
            props.put("retries",0);
            props.put("batch.size",16384);
            props.put("linger.ms",1);
            //设置回调模式为同步模式
            props.put("producer.type","sync");
            props.put("buffer.memory",33554432);
            //自定义分区
            props.put("partitioner","com.producer.SimplePartitioner1");
            props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
            Producer<String,String> producer=new KafkaProducer<String, String>(props);
            System.out.println("producer start.........");
            int i=0;
            while (true){
                i++;
//                 producer.send(new ProducerRecord<String, String>("test",Integer.toString(i),"hello world - "+i));
                 ProducerRecord<String,String> rec=new ProducerRecord<String, String>("test",Integer.toString(i),"wwww-"+i);
                 producer.send(rec, new Callback() {
                     @Override
                     public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                         System.out.println("received ack:");
                     }
                 });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
//        producer.close();
    }
}
