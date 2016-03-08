package com.qijiabin.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class HashTest {
	
	private static JedisPool jedisPool;
	private static Jedis jedis;
	
	@BeforeClass
	public static void before() {
		jedisPool = new JedisPool("localhost", 6379);
		jedis = jedisPool.getResource();
	}
	
	@Test
	public void testHash() {
		//1. 存放数据：使用HashMap
		Map<String, String>  capital = new HashMap<String, String>();
		capital.put("shannxi", "xi'an");
		capital.put("sichuan", "chengdu");
		jedis.hmset("capital", capital);
		//2. 获取数据：
		List<String> cities = jedis.hmget("capital", "shannxi", "sichuan");
		for (String string : cities) {
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
