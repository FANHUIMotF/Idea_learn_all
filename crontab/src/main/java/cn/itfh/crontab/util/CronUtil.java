package cn.itfh.crontab.util;

import org.springframework.scheduling.support.CronSequenceGenerator;

import java.util.Date;

/***
 *
 *  @className: CronUtil
 *  @author: fh
 *  @date: 2020/7/29
 *  @version : V1.0
 */
public class CronUtil {
    /**
     * 返回传入日期的下一个执行日期
     * @param cron
     * @param startTime
     * @return
     */
    public static Date getNextExecuteTime(String cron,Date startTime){
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(cron);
        return cronSequenceGenerator.next(startTime);
    }
}
