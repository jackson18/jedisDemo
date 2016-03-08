package cn.outofmemory.redis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import redis.clients.jedis.Jedis;

/**
 * ========================================================
 * 类说明：实现发布消息到redis的频道中
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class Publisher {

    private final Jedis publisherJedis;
    private final String channel;

    public Publisher(Jedis publisherJedis, String channel) {
        this.publisherJedis = publisherJedis;
        this.channel = channel;
    }

    public void start() {
        System.out.println("Type your message (quit for terminate)");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String line = reader.readLine();
                if (!"quit".equals(line)) {
                    publisherJedis.publish(channel, line);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("IO failure while reading input");
        }
    }
    
}