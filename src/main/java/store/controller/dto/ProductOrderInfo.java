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

    public int getGetAmount() {
        if (!isPromote) {
            return 0;
        }
        int promotionBuy = product.getPromotionBuy();
        int promotionGet = product.getPromotionGet();
        return (quantity / (promotionBuy + promotionGet)) * promotionGet;
    }

    public String getProductName() {
        return product.getName();
    }

    public int calculateTotalPrice() {
        return product.getPrice() * quantity;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public boolean isPromote() {
        return isPromote;
    }
}
