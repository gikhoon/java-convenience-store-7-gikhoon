package store.controller.dto;

import java.util.List;
import store.model.entity.Product;

public record ProductInfoDto(
        List<ProductInfo> productInfo
) {
    public static ProductInfoDto toDto(List<Product> products) {
        return new ProductInfoDto(
                products.stream()
                        .map(ProductInfo::from)
                        .toList()
        );
    }
}
