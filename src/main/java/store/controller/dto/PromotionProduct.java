package store.controller.dto;

import java.util.List;

public record PromotionProduct(
        List<PromotionProductInfo> products
) {
    public int calculateTotalPromotionDiscount() {
        return products.stream()
                .mapToInt(PromotionProductInfo::price)
                .sum();
    }
}
