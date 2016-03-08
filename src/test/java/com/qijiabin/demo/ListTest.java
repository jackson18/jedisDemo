package com.qijiabin.demo;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class ListTest {
	
	private static JedisPool jedisPool;
	private static Jedis jedis;
	
	@BeforeClass
	public static void before() {
		jedisPool = new JedisPool("localhost", 6379);
		jedis = jedisPool.getResource();
	}
	
	/**
	 * 可以使用列表模拟队列(queue)、堆栈(stack)，并且支持双向的操作(L或者R)
	 */
	@Test
	public void testList() {
		//右边入队
		jedis.rpush("userList", "James");
		//左边出队：右边出栈(rpop)，即为对堆栈的操作。
		String value = jedis.lpop("userList");
		System.out.println(value);
		
		jedis.rpush("userList", "a");
		jedis.rpush("userList", "b");
		jedis.rpush("userList", "c");
		//返回列表范围：从0开始，到最后一个(-1) [包含] 
		List<String> userList = jedis.lrange("userList", 0, -1);
		for (String string : userList) {
			System.out.println(string);
		}
		
		//设置：位置1处为新值
		jedis.lset("userList", 1, "Nick Xu");
		// 返回长度：
		Long size = jedis.llen("userList");
		System.out.println(size);
		//删除：使用key
		jedis.del("userList");
	}
	
	@AfterClass
	public static void after() {
		if (jedis != null) {
			jedis.close();
		}
		jedisPool.destroy();
	}
}
