package store.controller.dto;

import java.util.List;

public record OrderProduct(
        List<ReceiptProductInfo> products
) {
}
