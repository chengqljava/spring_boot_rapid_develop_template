package com.hwsafe.template.base.config;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;

/*
 * spring boot框架中已经集成了redis，
 * 在1.x.x的版本时默认使用的jedis客户端，
 * 现在是2.x.x版本默认使用的lettuce客户端，两种客户端的区别如下
 * # Jedis和Lettuce都是Redis Client

# Jedis 是直连模式，在多个线程间共享一个 Jedis 实例时是线程不安全的，
# 如果想要在多线程环境下使用 Jedis，需要使用连接池，
# 每个线程都去拿自己的 Jedis 实例，当连接数量增多时，物理连接成本就较高了。
# Lettuce的连接是基于Netty的，连接实例可以在多个线程间共享，
# 所以，一个多线程的应用可以使用同一个连接实例，而不用担心并发线程的数量。
# 当然这个也是可伸缩的设计，一个连接实例不够的情况也可以按需增加连接实例。

# 通过异步的方式可以让我们更好的利用系统资源，而不用浪费线程等待网络或磁盘I/O。
# Lettuce 是基于 netty 的，netty 是一个多线程、事件驱动的 I/O 框架，
# 所以 Lettuce 可以帮助我们充分利用异步的优势。
 */
//@Configuration
//@EnableCaching // 开启缓存支持
public class RedisAutoConfiguration   extends CachingConfigurerSupport{

	@Resource
	private LettuceConnectionFactory lettuceConnectionFactory;
 
 
	@Bean
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuffer sb = new StringBuffer();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				for (Object obj : params) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};
	}


 
	// 缓存管理器
	@Bean
	public CacheManager cacheManager() {
		RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder
				.fromConnectionFactory(lettuceConnectionFactory);
		@SuppressWarnings("serial")
		Set<String> cacheNames = new HashSet<String>() {
			{
				add("codeNameCache");
			}
		};
		builder.initialCacheNames(cacheNames);
		return builder.build();
	}
 
 
	/**
	 * RedisTemplate配置
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
		// 设置序列化
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
				Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
	    om.enableDefaultTyping(DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		// 配置redisTemplate
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(lettuceConnectionFactory);
		RedisSerializer<?> stringSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(stringSerializer);// key序列化
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);// value序列化
		redisTemplate.setHashKeySerializer(stringSerializer);// Hash key序列化
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);// Hash value序列化
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}



}