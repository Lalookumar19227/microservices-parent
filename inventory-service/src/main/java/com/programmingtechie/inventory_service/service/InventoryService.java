package com.programmingtechie.inventory_service.service;

///import com.programmingtechie.inventoryservice.repository.InventoryRepository;
import com.programmingtechie.inventory_service.dto.InventoryResponse;
import com.programmingtechie.inventory_service.repository.InventoryRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//@RequiredArgsConstructor
//@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode){

        return  inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                    InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity() > 0)
                            .build()
                ).toList();
    }
//    @Transactional(readOnly = true)
//    @SneakyThrows
//    public List<InventoryResponse> isInStock(List<String> skuCode) {
////        log.info("Checking Inventory");
//        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
//                .map(inventory ->
//                        InventoryResponse.builder()
//                                .skuCode(inventory.getSkuCode())
//                                .isInStock(inventory.getQuantity() > 0)
//                                .build()
//                ).toList();
//    }


}