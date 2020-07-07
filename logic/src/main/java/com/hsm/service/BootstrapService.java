package com.hsm.service;

import com.hsm.cache.AccountCaffeineCache;
import com.hsm.cache.AccountGuavaCache;

import java.util.concurrent.atomic.AtomicInteger;

public class BootstrapService {


    public AtomicInteger index = new AtomicInteger(0);

    public void startGuavaCache(){

        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (int i = 0; i < 8; i++){
            new Thread(()->{
                long startTime = System.currentTimeMillis();
                while (index.get() < 1000000){
                    AccountGuavaCache.INSTANCE.get(1);
                    index.incrementAndGet();
                }
                long endTime = System.currentTimeMillis();
                atomicInteger.addAndGet((int) (endTime - startTime));
            }).start();
        }
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("pass Time: " + atomicInteger.get());

    }

    public void startCaffeineCache(){

        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (int i = 0; i < 8; i++){
            new Thread(()->{
                long startTime = System.currentTimeMillis();
                while (index.get() < 1000000){
                    AccountCaffeineCache.INSTANCE.get(1);
                    index.incrementAndGet();
                }
                long endTime = System.currentTimeMillis();
                atomicInteger.addAndGet((int) (endTime - startTime));
            }).start();
        }
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("pass Time: " + atomicInteger.get());
    }


}
