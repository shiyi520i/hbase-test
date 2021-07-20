package com.example.test.config;

import com.example.test.utils.HBaseTemplate;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@org.springframework.context.annotation.Configuration
//hbase配置
public class HbaseConfig {
    @Bean
    public  HBaseTemplate configuration() throws IOException {
        Configuration conf = HBaseConfiguration.create();
          //zk入口ip
          conf.set("hbase.zookeeper.quorum", "192.168.10.100");
          //zk的连接端口
          conf.set("hbase.zookeeper.property.clientPort", "2181");
        Connection  connection = ConnectionFactory.createConnection(conf);
      return new HBaseTemplate(connection);
    }
}
