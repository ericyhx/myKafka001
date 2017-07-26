package com.producer;


import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

import java.util.Map;

/**
 * Created by dasun on 2017/7/24.
 * 简单的分区函数
 */
public class SimplePartitioner implements Partitioner {
    public SimplePartitioner(VerifiableProperties properties) {
    }

    public int partition(Object key, int numPartition){
        int partition=0;
        int iKey= (int) key;
        if(iKey>0){
            partition=iKey%numPartition;
        }
        return partition;
    }
}
