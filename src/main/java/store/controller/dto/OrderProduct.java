package store.controller.dto;

import java.util.List;

public record OrderProduct(
        List<ProductInfo> products
) {

    public record ProductInfo(
            String name,
            Integer quantity,
            Integer price
    ) {
    }
}
