package store.controller.dto;

import store.model.entity.Product;

public record ProductInfo(
        String name,
        String price,
        String quantity,
        String promotion
) {
    public static ProductInfo from(Product product) {
        return new ProductInfo(
                product.getName(),
                product.getPriceToString(),
                product.getQuantityToString(),
                product.getPromotionName()
        );
    }
}
