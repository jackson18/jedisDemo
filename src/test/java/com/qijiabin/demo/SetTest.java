package com.qijiabin.demo;

import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class SetTest {
	
	private static JedisPool jedisPool;
	private static Jedis jedis;
	
	@BeforeClass
	public static void before() {
		jedisPool = new JedisPool("localhost", 6379);
		jedis = jedisPool.getResource();
	}
	
	@Test
	public void testSet() {
		//添加到set：可一次添加多个
		jedis.sadd("fruit", "apple");
		jedis.sadd("fruit", "pear");
		jedis.sadd("fruit", "watermelon");
		
		//遍历集合：
		Set<String> fruit = jedis.smembers("fruit");
		for (String string : fruit) {
			System.out.println(string);
		}
		//移除元素：remove
		jedis.srem("fruit", "pear");
		// 返回长度：
		Long size = jedis.scard("fruit");
		System.out.println(size);
		//是否包含：
		Boolean isMember = jedis.sismember("fruit", "pear");
		System.out.println(isMember);
		//集合的操作：包括集合的交运算(sinter)、差集(sdiff)、并集(sunion)
		jedis.sadd("food", "bread"); 
		Set<String> fruitFood = jedis.sunion("fruit", "food");
		for (String string : fruitFood) {
			System.out.println(string);
		}
	}
	
	@AfterClass
	public static void after() {
		if (jedis != null) {
			jedis.close();
		}
		jedisPool.destroy();
	}
}
