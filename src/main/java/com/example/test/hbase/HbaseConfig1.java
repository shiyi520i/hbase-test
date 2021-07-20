package com.example.test.hbase;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 创建时间 2021/7/2 21:49
 *
 * @author Ye
 * @version 1.0
 */
/*@org.springframework.context.annotation.Configuration
public class HbaseConfig1 {
    static Configuration conf=null;
    @Bean
    public static Configuration configuration(){
        conf = HBaseConfiguration.create();
        //zk入口ip
        //conf.set("hbase.rootdir", "hdfs://hadoop:9000/hbase");
        conf.set("hbase.zookeeper.quorum","hadoop,hadoop1,hadoop2");
        return conf;
    }
}*/
