package com.programmingtechie.inventory_service.dto;

//import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponse {


    private String skuCode;
    private boolean isInStock;

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public boolean isInStock() {
        return isInStock;
    }

    public void setInStock(boolean inStock) {
        isInStock = inStock;
    }

    public InventoryResponse(String skuCode, boolean isInStock) {
        this.skuCode = skuCode;
        this.isInStock = isInStock;



    }


}