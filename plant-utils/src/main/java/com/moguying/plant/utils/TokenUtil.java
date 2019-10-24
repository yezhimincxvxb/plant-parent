package com.moguying.plant.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.moguying.plant.constant.ApiEnum;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.params.SetParams;

import java.util.Date;
import java.util.Map;

@Slf4j
public enum TokenUtil {
    INSTANCE;
    //单位毫秒 3天;
    public Long activeTime = 3 * 24 * 60 * 60 * 1000L;
    //单位毫秒  5天;
    private Long expire = 5 * 24 * 60 * 60 * 1000L;

    Algorithm algorithm = Algorithm.HMAC256("1%39(]Si*B1+2*JKsmwNv");

    public String addToken(Map<String,String> claims){
        SetParams params = new SetParams();
        params.ex(expire.intValue()/1000);
        params.nx();
        return setToRedis(params,claims);

    }

    public String updateToken(Map<String,String> claims){
        SetParams params = new SetParams();
        params.ex(expire.intValue()/1000);
        params.xx();
        return setToRedis(params,claims);
    }

    private String setToRedis(SetParams params,Map<String,String> claims){
       return  generateToken(claims);
        /*
        IOS进程退出后会将token数据清除，暂时无法做单用户登录
        RedisUtil redisUtil = new RedisUtil();
        Jedis jedis = redisUtil.getJedis();
        jedis.set("user:token:"+claims.get(ApiEnum.LOGIN_USER_ID.getTypeStr()),token,params);
        redisUtil.releaseJedis(jedis);
         */
    }

    private String generateToken(Map<String,String> claims){
        long expireTime = System.currentTimeMillis() + expire;
        return JWT.create()
                .withClaim(ApiEnum.LOGIN_USER_ID.getTypeStr(),claims.get(ApiEnum.LOGIN_USER_ID.getTypeStr()))
                .withClaim(ApiEnum.LOGIN_PHONE.getTypeStr(),claims.get(ApiEnum.LOGIN_PHONE.getTypeStr()))
                .withClaim(ApiEnum.LOGIN_TIME.getTypeStr(),claims.get(ApiEnum.LOGIN_TIME.getTypeStr()))
                .withIssuedAt(new Date())
                .withNotBefore(new Date())
                .withExpiresAt(new Date(expireTime))
                .sign(algorithm);

    }

    public DecodedJWT verify(String token) throws JWTVerificationException {
        return JWT.require(algorithm).build().verify(token);
    }


}
