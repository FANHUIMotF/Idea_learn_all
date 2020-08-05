package cn.itfh.crontab.entity;

import java.io.Serializable;
import java.util.Date;

/***
 *  定时任务表 对应的实体类
 *  @className: CronTaskEntity
 *  @author: fh
 *  @date: 2020/7/29
 *  @version : V1.0
 */
public class CronTaskEntity implements Serializable {
    /**
     * 任务执行类简称（spring中注入的名称）
     */
    private String className;

    /**
     * 是否启用；0-不启用，1-启用
     */
    private String isUser;
    /**
     * 执行版本
     */
    private int version;
    /**
     * cron表达式
     */
    private String corn;
    /**
     * 上次执行时间
     */
    private Date lastTime;
    /**
     * 下次执行时间
     */
    private Date nextTime;
    /**
     * 当前状态；0-等待执行，1-执行中
     */
    private String status;
    /**
     * 执行结果.0:失败，1：成功
     */
    private String result;
    /**
     * 结果描述.
     */
    private String resultDesc;
    /**
     * 备注
     */
    private String remark;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    public String getIsUser() {
        return isUser;
    }

    public void setIsUser(String isUser) {
        this.isUser = isUser;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getCorn() {
        return corn;
    }

    public void setCorn(String corn) {
        this.corn = corn;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public Date getNextTime() {
        return nextTime;
    }

    public void setNextTime(Date nextTime) {
        this.nextTime = nextTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
