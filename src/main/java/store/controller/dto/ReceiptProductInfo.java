package store.controller.dto;

import static store.constant.FormatConstant.RECEIPT_PRODUCT_FORMAT;

public record ReceiptProductInfo(
        String name,
        Integer quantity,
        Integer price
) {
    @Override
    public String toString() {
        return String.format(RECEIPT_PRODUCT_FORMAT, name, quantity, price);
    }
}
