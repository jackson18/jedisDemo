package com.qijiabin.demo;

import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

public class OtherTest {
	
	private static JedisPool jedisPool;
	private static Jedis jedis;
	
	@BeforeClass
	public static void before() {
		jedisPool = new JedisPool("localhost", 6379);
		jedis = jedisPool.getResource();
	}
	
	@Test
	public void testOther() {
		//1. 对key的操作： 
		//@ 对key的模糊查询：
//		Set<String> keys = jedis.keys("*");
		Set<String> keys = jedis.keys("user.userid.*");
		for (String string : keys) {
			System.out.println(string);
		}
		//@ 删除key：
		jedis.del("city");
		//@ 是否存在：
		Boolean isExists = jedis.exists("user.userid.14101");
		System.out.println(isExists);
		
		//2. 失效时间： 
		//@ expire：时间为5s
		jedis.setex("user.userid.14101", 5, "James");
		//@ 存活时间(ttl)：time to live
		Long seconds = jedis.ttl("user.userid.14101");
		System.out.println(seconds);
		
		//3. 自增的整型： 
		//@ int类型采用string类型的方式存储：
		jedis.set("amount", 100 + "");
		//@ 递增或递减：incr()/decr()
		jedis.incr("amount");
		//@ 增加或减少：incrBy()/decrBy()
		jedis.incrBy("amount", 20);
		
		//4. 数据清空： 
		//@ 清空当前db：
		jedis.flushDB();
		//@ 清空所有db：
		jedis.flushAll();
		
		//5. 事务支持： 
		//@ 获取事务：
		Transaction tx = jedis.multi();
		//@ 批量操作：tx采用和jedis一致的API接口
		try {
			for(int i = 0;i < 10;i ++) {
			     tx.set("key" + i, "value" + i); 
			     System.out.println("--------key" + i);
			     Thread.sleep(500);  
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//@ 执行事务：针对每一个操作，返回其执行的结果，成功即为Ok
		List<Object> results = tx.exec();
		for (Object object : results) {
			System.out.println(object);
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
