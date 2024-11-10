package store.controller.dto;

import store.constant.OrderProductConstant;

public class OrderNameInfo {
    private String productName;
    private Integer quantity;

    public OrderNameInfo(String productName, Integer quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public static OrderNameInfo mapToProductOrderInfo(String data) {
        data = removePrefixSuffix(data);
        String[] splitData = data.split(OrderProductConstant.PRODUCT_SPLIT_SEPARATOR);
        return new OrderNameInfo(splitData[0], Integer.parseInt(splitData[1]));
    }

    private static String removePrefixSuffix(String data) {
        return data.replace(OrderProductConstant.PRODUCT_PREFIX, "")
                .replace(OrderProductConstant.PRODUCT_SUFFIX, "");
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
