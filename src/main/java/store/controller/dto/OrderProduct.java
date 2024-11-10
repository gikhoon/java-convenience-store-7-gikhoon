package store.controller.dto;

import java.util.List;

public record OrderProduct(
        List<ReceiptProductInfo> products
) {
    public int calculateTotalQuantity() {
        return products.stream()
                .mapToInt(ReceiptProductInfo::quantity)
                .sum();
    }

    public int calculateTotalPrice() {
        return products.stream()
                .mapToInt(ReceiptProductInfo::price)
                .sum();
    }
}
