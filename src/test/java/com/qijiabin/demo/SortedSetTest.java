package com.qijiabin.demo;

import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class SortedSetTest {
	
	private static JedisPool jedisPool;
	private static Jedis jedis;
	
	@BeforeClass
	public static void before() {
		jedisPool = new JedisPool("localhost", 6379);
		jedis = jedisPool.getResource();
	}
	
	@Test
	public void testSortedSet() {
		//有序集合：根据“第二个参数”进行排序
		jedis.zadd("user", 22, "James");
		//再次添加：元素相同时，更新为当前的权重
		jedis.zadd("user", 24, "James");
		//zset的范围：找到从0到-1的所有元素
		Set<String> user = jedis.zrange("user", 0, -1);
		for (String string : user) {
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
