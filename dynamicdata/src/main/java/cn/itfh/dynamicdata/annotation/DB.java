package cn.itfh.dynamicdata.annotation;

import cn.itfh.dynamicdata.datasource.DataSourceNames;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 自定义数据源注解
 * 运行期间保留，mybatis通过此注解切换数据源
 * 注解在方法上
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DB {
    String value() default DataSourceNames.DB1;
}
