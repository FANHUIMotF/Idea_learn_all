package cn.itfh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//数据源是自己定义的，这里去除自动装配的数据源
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableTransactionManagement
public class DynamicdataApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicdataApplication.class, args);
    }

}
