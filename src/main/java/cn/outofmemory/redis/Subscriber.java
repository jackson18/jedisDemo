package cn.outofmemory.redis;

import redis.clients.jedis.JedisPubSub;

/**
 * ========================================================
 * 类说明：监听订阅事件 
 * jedis中提供了JedisPubSub抽象类来提供发布/订阅的机制，在实际应用中需要实现JedisPubSub类
 * ========================================================
 * 修订日期     修订人    描述
 */
public class Subscriber extends JedisPubSub {

	/**
	 * 初始化订阅时候的处理  
	 */
	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		System.out.println(channel + "=" + subscribedChannels);  
	}
	
	/**
	 * 取得订阅的消息后的处理 
	 */
    @Override
    public void onMessage(String channel, String message) {
        System.out.println("Message received. Channel: "+channel+", Msg: "+message+"");
    }
    
    /**
     * 取消订阅时候的处理  
     */
    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
    	System.out.println(channel + "=" + subscribedChannels);  
    }

    /**
     * 初始化按表达式的方式订阅时候的处理  
     */
    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
    	System.out.println(pattern + "=" + subscribedChannels);  
    }
    
    /**
     * 取得按表达式的方式订阅的消息后的处理  
     */
    @Override
    public void onPMessage(String pattern, String channel, String message) {
    	System.out.println(pattern + "=" + channel + "=" + message);  
    }

    /**
     * 取消按表达式的方式订阅时候的处理  
     */
    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
    	 System.out.println(pattern + "=" + subscribedChannels);  
    }

}