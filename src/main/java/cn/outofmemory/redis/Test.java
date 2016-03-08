package cn.outofmemory.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * ========================================================
 * 类说明：使用JedisPool来获得Jedis的实例。 Jedis类是非线程安全的，但是JedisPool是线程安全的。我使用了两个Jedis实例，一个用来发布消息，另一个订阅频道。
 * 关键点如下：
 * 要在单独的线程中订阅, 因为subscribe会阻塞当前线程的执行。 你可以使用一个PubSub实例来订阅多个Channel
 * 注意Jedis实例是非线程安全的
 * 当需要创建多个jedis实例时要使用JedisPool类，当使用完jedis对象时要放回JedisPool。
 * ========================================================
 * 修订日期     修订人    描述
 */
public class Test {

	private static JedisPoolConfig poolConfig = new JedisPoolConfig();
	//jedis的连接池
	private static JedisPool jedisPool = new JedisPool(poolConfig, "localhost", 6379, 0);
	private static final Jedis subscriberJedis = jedisPool.getResource();
	private static final Jedis publisherJedis = jedisPool.getResource();
	//监听订阅事件对象
	private static final Subscriber subscriber = new Subscriber();
	//频道
    private static final String CHANNEL_NAME = "commonChannel";

    
    @SuppressWarnings({ "deprecation" })
	public static void main(String[] args) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Subscribing to \"commonChannel\". This thread will be blocked.");
                    subscriberJedis.subscribe(subscriber, CHANNEL_NAME);
                    System.out.println("Subscription ended.");
                } catch (Exception e) {
                    System.out.println("Subscribing failed.");
                }
            }
        }).start();

        new Publisher(publisherJedis, CHANNEL_NAME).start();

        subscriber.unsubscribe();
        jedisPool.returnResource(subscriberJedis);
        jedisPool.returnResource(publisherJedis);
    }
    
}