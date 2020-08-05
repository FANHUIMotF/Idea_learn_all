package cn.itfh.crontab.util;

import java.util.HashMap;
import java.util.Map;

/***
 *  常量类
 *  @className: Constant
 *  @author: fh
 *  @date: 2020/7/29
 *  @version : V1.0
 */
public class Constant {
    public static int MAX_VERSION = 999999;

    public enum Cron {
        IsUser_False("0", "不启用"), IsUser_True("1", "启用"), Result_Fail("0", "失败"),
        Result_Success("1", "成功"), Status_Wait("0", "等待执行"), Status_Running("1", "运行中");
        /**
         * 对应的值
         */
        private String key;
        /**
         * 显示的中文名称
         */
        private String displayName;
        public String getKey(){
            return key;
        }

        public String getDisplayName(){
            return displayName;
        }

        Cron(String key, String displayName) {
            this.key = key;
            this.displayName = displayName;
        }

        private static Map<String, Cron> keyMap;
        private static Map<String, Cron> displayNameMap;

        static {
            keyMap = new HashMap<>(6);
            displayNameMap = new HashMap<>(6);
            for (Cron date : Cron.values()) {
                keyMap.put(date.getKey(),date);
                displayNameMap.put(date.getDisplayName(),date);
            }
        }
    }
}
