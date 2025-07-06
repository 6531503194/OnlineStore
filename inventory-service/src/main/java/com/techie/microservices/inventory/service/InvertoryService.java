package com.techie.microservices.inventory.service;

import org.springframework.stereotype.Service;

import com.techie.microservices.inventory.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvertoryService {
    
    private final InventoryRepository inventoryRepository;
    
    public boolean isInStock(String skuCode , Integer quantity) {
        return inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode,quantity);
    }
}
