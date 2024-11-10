package store.controller.dto;

import java.util.List;

public record PromotionProduct(
        List<PromotionProductInfo> products
) {
    record PromotionProductInfo(
            String name,
            Integer quantity
    ) {
    }
}
