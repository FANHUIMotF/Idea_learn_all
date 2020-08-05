package cn.itfh.crontab.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/***
 *
 *  @className: BeanUtil
 *  @author: fh
 *  @date: 2020/8/5
 *  @version : V1.0
 */
@Component
public class BeanUtil {

    static ApplicationContext applicationContext;
    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext){
        BeanUtil.applicationContext = applicationContext;
    }

    public static Object getBean(String beanName){
        return applicationContext.getBean(beanName);
    }
}
