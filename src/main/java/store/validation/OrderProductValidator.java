package store.validation;

import static store.constant.OrderProductConstant.PRODUCT_FORMAT;

import java.util.List;
import store.exception.ErrorCode;
import store.model.entity.Product;

public class OrderProductValidator {
    public void validateProductOrderFormat(String order) {
        if (!order.matches(PRODUCT_FORMAT)) {
            throw new IllegalArgumentException(ErrorCode.ORDER_PRODUCT_FORMAT_ERROR.getMessage());
        }
    }

    public void validateSufficientProductQuantity(List<Product> activeProduct, Integer quantity) {
        int totalQuantity = activeProduct.stream()
                .mapToInt(Product::getQuantity)
                .sum();
        if (quantity > totalQuantity) {
            throw new IllegalArgumentException(ErrorCode.NOT_SUFFICIENT_QUANTITY.getMessage());
        }
    }
}
