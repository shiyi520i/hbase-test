package com.example.test.conf;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
//hbase配置
public class HbaseConfig {
    Configuration conf=null;
    @Bean
    public Configuration configuration(){
          conf = HBaseConfiguration.create();
          //zk入口ip
          conf.set("hbase.zookeeper.quorum", "192.168.10.100");
          //zk的连接端口
          conf.set("hbase.zookeeper.property.clientPort", "2181");
      return conf;
    }
}
