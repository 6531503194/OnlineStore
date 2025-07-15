package com.techie.microservices.order.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;


public interface InventoryClient {
    
    Logger log = LoggerFactory.getLogger(InventoryClient.class);

    @GetExchange("/api/inventory")
    //this name will be match with the application.properties file
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @Retry(name="inventory")
    boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);

    default boolean fallbackMethod(String skuCode, Integer quantity, Throwable t) {
        log.info("Fallback method called for isInStock with skuCode: {}, quantity: {}, error: {}", skuCode, quantity, t.getMessage());
        return false; 
    }

   
}
