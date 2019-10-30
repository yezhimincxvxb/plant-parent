package com.moguying.plant.utils;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class RedisUtil {


    public RedisUtil() {
    }

    private static int PORT = 6869;

    private static String HOST = "172.18.146.177";

    private static String PASSWORD = "AEnz2DF\\Od,GWD#C*+";

    private final static int MAX_IDLE = 200;

    private final static int MAX_WAIT = 10000;

    private final static int TIME_OUT = 10000;
    private static JedisPool jedisPool = null;

    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(false);
            jedisPool = new JedisPool(config,HOST,PORT,TIME_OUT,PASSWORD);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public synchronized Jedis getJedis(){
        Jedis jedis = null;
        try {
            if (null != jedisPool) {
                 jedis = jedisPool.getResource();
                return jedis;
            } else
                return null;
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(null != jedis)
                jedis.close();
        }
        return null;
    }

    public void releaseJedis(Jedis jedis){
        if(null != jedis)
            jedis.close();
    }
}
