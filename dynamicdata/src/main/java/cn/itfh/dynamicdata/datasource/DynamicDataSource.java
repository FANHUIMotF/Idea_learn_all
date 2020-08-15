package cn.itfh.dynamicdata.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/***
 *  數據源路由，由此类选择执行的操作的数据源
 *  @className: DynamicDataSource
 *  @author: fh
 *  @date: 2020/8/11
 *  @version : V1.0
 */

public class DynamicDataSource extends AbstractRoutingDataSource {
    /**
     * 此方法决定使用哪个数据源
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContext.getDB();
    }

}
