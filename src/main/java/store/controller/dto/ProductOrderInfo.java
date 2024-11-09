package store.controller.dto;

import store.model.entity.Product;

public class ProductOrderInfo {
    private Product product;
    private Integer quantity;

    public ProductOrderInfo(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}
