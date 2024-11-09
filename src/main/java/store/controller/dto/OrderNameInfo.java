package store.controller.dto;

public class OrderNameInfo {
    private String productName;
    private Integer quantity;

    public OrderNameInfo(String productName, Integer quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public void addQuantity(int extraProduct) {
        quantity += extraProduct;
    }

    public void deleteQuantity(int remainProduct) {
        quantity -= remainProduct;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
