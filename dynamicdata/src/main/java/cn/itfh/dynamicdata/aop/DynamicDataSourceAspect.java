package cn.itfh.dynamicdata.aop;

import cn.itfh.dynamicdata.annotation.DB;
import cn.itfh.dynamicdata.datasource.DataSourceNames;
import cn.itfh.dynamicdata.datasource.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/***
 *  动态数据源aop切点
 *  @className: DynamicDataSourceAspect
 *  @author: fh
 *  @date: 2020/8/11
 *  @version : V1.0
 */
@Aspect
@Component
@Slf4j
/**
 * 確保此注解在@Transaction之前，防止與@Transaction產生衝突
 */
@Order(-1)
public class DynamicDataSourceAspect {
    /**
     * 切点
     */
    @Pointcut("@annotation(cn.itfh.dynamicdata.annotation.DB)")
    private void cut() {

    }

    /**
     * 环绕通知
     *
     * @param joinPoint
     */
    @Around("cut()")
    public Object dynamicDataSource(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取拦截的方法
        Method method = signature.getMethod();

        //存在数据源注解
        if (method.isAnnotationPresent(DB.class)) {
            //获取注解
            DB annotation = method.getAnnotation(DB.class);
            String value = annotation.value();
            DynamicDataSource.setDB(value);
        }
        //无此 注解 ，使用默认数据源
        else {
            DynamicDataSource.setDB(DataSourceNames.DB1);
        }

        try {
            //方法执行,返回查询结果
            return joinPoint.proceed();
        } finally {
            //清空数据源
            DynamicDataSource.clearDB();
        }
    }
}
