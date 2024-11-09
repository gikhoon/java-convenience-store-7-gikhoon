package store.validation;

import static store.constant.OrderProductConstant.PRODUCT_FORMAT;

import store.exception.ErrorCode;

public class OrderProductValidator {
    public static void validateProductOrderFormat(String order) {
        if (!order.matches(PRODUCT_FORMAT)) {
            throw new IllegalArgumentException(ErrorCode.ORDER_PRODUCT_FORMAT_ERROR.getMessage());
        }
    }
}
