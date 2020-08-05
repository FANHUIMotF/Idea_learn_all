package cn.itfh.crontab;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@MapperScan(basePackages = "cn.itfh.crontab.dao")
// 开启定时任务功能
@EnableScheduling
public class CrontabApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrontabApplication.class, args);
    }

}
