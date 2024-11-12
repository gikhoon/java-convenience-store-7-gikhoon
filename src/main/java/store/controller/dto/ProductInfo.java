package store.controller.dto;

import store.constant.FormatConstant;
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

    @Override
    public String toString() {
        return String.format(
                FormatConstant.PRODUCT_INFO_FORMAT,
                name, price, quantity, promotion
        );
    }
}
