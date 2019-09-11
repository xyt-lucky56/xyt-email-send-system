package com.xyt.utils;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoRadiusCommandArgs;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.alibaba.fastjson.JSON;

/**
 * redis 工具类
 * 
 * @ClassName: RedisUtils
 * @author: 武汉信运通
 * @date: 2018年3月9日 下午2:26:55
 */
public class RedisUtils {
	private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);
	public static final int TOKEN_EXPIRE_TOKEN   = 10080;//7天
	public static final int PASSWORD_EXPIRE_TOKEN   = 11520;//8天
	public static final int USERINFO_EXPIRE_TOKEN   = 30;//半小时

	public static StringRedisTemplate getTemplate() {
		return SpringContextHolder.getBean(StringRedisTemplate.class);
	}


	/**
	 * 
	 * redis 对象操作
	 */
	public static <T> T get(String key, Class<T> clazz) {
		String value = getTemplate().opsForValue().get(key);
		return JSON.parseObject(value, clazz);
	}

//	public static void set(String key, Object value, int expireTime) {
//		String json = JSON.toJSONString(value);
//		set(key, value, expireTime);
//	}

	public static Boolean hasKey(String key) {
		return getTemplate().hasKey(key);
	}

	/**
	 * 
	 * redis 字符串操作
	 */

	public static String get(String key) {
		return getTemplate().opsForValue().get(key);
	}

	public static void set(String key, String value) {
		getTemplate().opsForValue().set(key, value);
	}

	public static void set(String key, String value, int expireTime) {
		set(key, value, expireTime, TimeUnit.MINUTES);
	}

	public static void set(String key, String value, int expireTime, TimeUnit timeUnit) {
		set(key, value);
		expire(key, expireTime, timeUnit);
	}

	public static void delete(String key) {
		getTemplate().delete(key);
	}

	/**
	 * HashMap 操作
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public static String hget(String key, String field) {
		HashOperations<String, String, String> ho = getTemplate().opsForHash();
		return ho.get(key, field);
	}

	public static Long hdelete(String key, String field) {
		return getTemplate().opsForHash().delete(key, field);
	}

	public static void hset(String key, Map<String, String> map, int expireTime) {
		hset(key, map, expireTime, TimeUnit.MINUTES);
	}

	public static void hset(String key, Map<String, String> map, int expireTime, TimeUnit timeUnit) {
		getTemplate().opsForHash().putAll(key, map);
		expire(key, expireTime, timeUnit);
	}

	/**
	 * 失效时间
	 */
	public static void expire(String key, int expireTime, TimeUnit timeUnit) {
		if (expireTime > 0) {
			getTemplate().expire(key, expireTime, timeUnit);
		}
	}

	/**
	 * 坐标读取
	 */
	public static GeoResults<GeoLocation<String>> nearby(String key, double x, double y, double radius) {
		Circle circle = new Circle(new Point(x, y), new Distance(radius, Metrics.KILOMETERS));
		return nearby(key, circle);
	}

	public static GeoResults<GeoLocation<String>> nearby(String key, Circle circle) {
		return getTemplate().opsForGeo().geoRadius(key, circle,
				GeoRadiusCommandArgs.newGeoRadiusArgs().includeCoordinates().includeDistance());
	}

	public static void geopipelined(List<GeoLocation<String>> geoList, String key, int expireTime) {
		geopipelined(geoList, key, expireTime, TimeUnit.MINUTES);
	}

	public static void geopipelined(final List<GeoLocation<String>> geoList, final String key, final int expireTime,
			final TimeUnit timeUnit) {

		getTemplate().executePipelined(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection stringRedisConnection = (StringRedisConnection) connection;
				for (GeoLocation<String> geoLocation : geoList) {
					stringRedisConnection.geoAdd(key, geoLocation);
				}
				expire(key, expireTime, timeUnit);
				return geoList.size();
			}
		});
	}

	/**
	 * 管道操作
	 */
	public static void hpipelined(Map<String, String> dataMap, String key, int expireTime) {
		hpipelined(dataMap, key, expireTime, TimeUnit.MINUTES);
	}

	public static void hpipelined(final Map<String, String> dataMap, final String key, final int expireTime,
			final TimeUnit timeUnit) {
		getTemplate().executePipelined(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection stringRedisConnection = (StringRedisConnection) connection;
				for (Map.Entry<String, String> data : dataMap.entrySet()) {
					stringRedisConnection.hSet(key, data.getKey(), data.getValue());
				}

				expire(key, expireTime, timeUnit);
				return dataMap.size();
			}
		});
	}

	public static <T> List<Object> pipelined(RedisCallback<T> redisCallback) {
		return getTemplate().executePipelined(redisCallback);
	}

	/**
	 * 分布式锁
	 * 
	 * 存在的问题 1、lock 中 setIfAbsent 执行完成后redis挂掉，expire无法执行 则key不会失效
	 * 
	 * 2、release中 线程T1获取锁 线程T1执行业务操作，由于某些原因阻塞了较长时间 锁自动过期，即锁自动释放了 线程T2获取锁
	 * 线程T1业务操作完毕，释放锁（其实是释放的线程T2的锁）
	 */
	public static final int TIMEOUT_MILLIS = 30000;
	public static final int RETRY_TIMES = 100;
	public static final long SLEEP_MILLIS = 500;

	public static boolean lock(String key) {
		return lock(key, TIMEOUT_MILLIS, RETRY_TIMES, SLEEP_MILLIS);
	}

	public static boolean lock(String key, int retryTimes) {
		return lock(key, TIMEOUT_MILLIS, retryTimes, SLEEP_MILLIS);
	}

	public static boolean lock(String key, int retryTimes, long sleepMillis) {
		return lock(key, TIMEOUT_MILLIS, retryTimes, sleepMillis);
	}

	public static boolean lock(String key, int expire, int retryTimes) {
		return lock(key, expire, retryTimes, SLEEP_MILLIS);
	}

	public static boolean lock(String key, int expire, int retryTimes, long sleepMillis) {

		boolean result = setnx(key, expire);
		// 如果获取锁失败，按照传入的重试次数进行重试
		while ((!result) && retryTimes-- > 0) {
			try {
				logger.debug("redis lock failed, retrying..." + retryTimes);
				Thread.sleep(sleepMillis);
			} catch (InterruptedException e) {
				return false;
			}
			result = setnx(key, expire);
		}
		return false;
	}

	private static boolean setnx(String key, int expire) {
		String value = UUID.randomUUID().toString();
		boolean flag = getTemplate().opsForValue().setIfAbsent(key, value);
		expire(key, expire, TimeUnit.SECONDS);
		return flag;
	}

	public static void release(String key) {
		delete(key);
	}

	public static long increment(String key, long delta, int expireTime) {
		Long result = getTemplate().opsForValue().increment(key, delta);
		if (result != null && result.longValue() == 1) {
			expire(key, expireTime, TimeUnit.SECONDS);
		}
		return result==null?0:result;
	}
}
