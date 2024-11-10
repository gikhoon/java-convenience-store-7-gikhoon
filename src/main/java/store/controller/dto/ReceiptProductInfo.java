package store.controller.dto;

public record ReceiptProductInfo(
        String name,
        Integer quantity,
        Integer price
) {
}
