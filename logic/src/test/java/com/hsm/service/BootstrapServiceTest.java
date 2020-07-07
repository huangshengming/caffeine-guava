package com.hsm.service;


public class BootstrapServiceTest {

    @org.junit.Test
    public void startGuavaCache() {

        BootstrapService service = new BootstrapService();
        service.startGuavaCache();
    }

    @org.junit.Test
    public void startCaffeineCache() {
        BootstrapService service = new BootstrapService();
        service.startCaffeineCache();
    }
}
