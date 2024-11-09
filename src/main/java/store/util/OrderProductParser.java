package store.util;

import static store.constant.OrderProductConstant.PRODUCT_SUFFIX;
import static store.constant.OrderProductConstant.PRODUCT_SPLIT_SEPARATOR;
import static store.constant.OrderProductConstant.PRODUCT_PREFIX;

import java.util.Arrays;
import java.util.List;
import store.exception.ErrorCode;

public class OrderProductParser {
    public static List<String> parseOrder(String orders) {
        if (isEmpty(orders) || !isFormatValid(orders)) {
            throw new IllegalArgumentException(ErrorCode.ORDER_PRODUCT_FORMAT_ERROR.getMessage());
        }

        return Arrays.stream(orders.trim()
                        .split(PRODUCT_SPLIT_SEPARATOR))
                .toList();
    }

    private static boolean isFormatValid(String orders) {
        orders = orders.trim();
        return orders.startsWith(PRODUCT_PREFIX) && orders.endsWith(PRODUCT_SUFFIX);
    }

    private static boolean isEmpty(String orders) {
        return orders.trim().isEmpty();
    }
}
