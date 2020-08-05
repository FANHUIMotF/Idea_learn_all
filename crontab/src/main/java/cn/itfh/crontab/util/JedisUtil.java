package cn.itfh.crontab.util;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

/***
 *
 *  @className: JedisUtil
 *  @author: fh
 *  @date: 2020/7/29
 *  @version : V1.0
 */
@Component
public class JedisUtil {
    private static JedisPoolConfig jpc = null;
    private static JedisPool jp = null;
    private static String host = null;
    private static int port = 0;
    private static int max_idle;
    private static int max_total;
    public static String OK = "OK";
    static {
        ResourceBundle r = ResourceBundle.getBundle("redis");
        host = r.getString("host");
        port = Integer.parseInt(r.getString("port"));
        max_idle = Integer.parseInt(r.getString("max-idle"));
        max_total = Integer.parseInt(r.getString("max-total"));
        jpc = new JedisPoolConfig();
        jpc.setMaxTotal(max_total);
        jpc.setMaxIdle(max_idle);
        jp = new JedisPool(jpc, host, port);
    }

    public static Jedis getJedis() {
        return jp.getResource();
    }

    public static void main(String[] args) {
        Jedis jedis = JedisUtil.getJedis();
        String set = jedis.set("123", "123", "nx", "px", 10000L);
        System.out.println(set);
    }
}