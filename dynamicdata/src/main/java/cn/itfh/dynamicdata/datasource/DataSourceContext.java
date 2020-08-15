package cn.itfh.dynamicdata.datasource;

/***
 *
 *  @className: DataSourceContext
 *  @author: fh
 *  @date: 2020/8/11
 *  @version : V1.0
 */
public class DataSourceContext {
    /**
     * 线程变量，隔离各个线程之间的参数，防止多线程并发导致数据源混乱的问题
     */
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();
    /**
     * 设置数据源名
     * @param dbType
     */
    public static void setDB(String dbType) {
        contextHolder.set(dbType);
    }
    /**
     * 获取数据源名
     * @return
     */
    public static String getDB() {
        return (contextHolder.get());
    }
    /**
     * 清除数据源名
     */
    public static void clearDB() {
        contextHolder.remove();
    }
}
