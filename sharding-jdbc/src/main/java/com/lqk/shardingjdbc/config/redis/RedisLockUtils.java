package com.lqk.shardingjdbc.config.redis;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author liqiankun
 * @date 2024/12/2 10:09
 * @description
 **/
@Component
@Slf4j
public class RedisLockUtils {


    @Autowired
    private RedissonClient redisson;

    /**
     * 加锁 最多等待0秒，上锁以后5秒自动解锁
     *
     * @param key
     * @return
     */
    public Boolean tryLock(String key) {
        RLock lock = redisson.getLock(key);
        try {
            // 尝试加锁，最多等待0秒，上锁以后5秒自动解锁
            boolean res = lock.tryLock(0, 5, TimeUnit.SECONDS);
            // 尝试加锁上锁以后5秒自动解锁
            //lock.lock(5, TimeUnit.SECONDS);
            return res;
        } catch (Exception e) {
            log.error("加锁失败key:" + key + ";" + e.getMessage());
        }
        return false;
    }

    /**
     * 加锁 waitTime 等待时间 秒 leaseTime 释放时间 秒
     *
     * @param key
     * @return
     */
    public Boolean tryLock(String key, int waitTime, int leaseTime) {
        RLock lock = redisson.getLock(key);
        try {
            // 尝试加锁，最多等待0秒，上锁以后5秒自动解锁
            boolean res = lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
            return res;
        } catch (Exception e) {
            log.error("加锁失败key:" + key + ";" + e.getMessage());
        }
        return false;
    }

    /**
     * 解锁
     *
     * @param key
     */
    public void unlock(String key) {
        RLock lock = redisson.getLock(key);
        lock.unlock();
    }

    /**
     * 当前线程持有才能解锁：redis key必须有超时时间
     *
     * @param key
     */
    public void unlockIfHeldByCurrentThread(String key) {
        RLock lock = redisson.getLock(key);

        // 当前线程持有，则解锁
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }
}
