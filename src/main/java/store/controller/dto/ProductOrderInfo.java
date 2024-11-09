package store.controller.dto;

import store.model.entity.Product;

public class ProductOrderInfo {
    private Product product;
    private Integer quantity;
    private boolean isPromote;

    public ProductOrderInfo(Product product, Integer quantity, boolean isPromote) {
        this.product = product;
        this.quantity = quantity;
        this.isPromote = isPromote;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
