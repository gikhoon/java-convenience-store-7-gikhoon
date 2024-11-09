package store.util;

import java.util.Arrays;
import java.util.List;
import store.exception.ErrorCode;

public class OrderProductParser {
    private static final String PRODUCT_SPLIT_SEPARATOR = ",";
    private static final String PRODUCT_START_PREFIX = "[";
    private static final String PRODUCT_END_PREFIX = "]";

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
        return orders.startsWith(PRODUCT_START_PREFIX) && orders.endsWith(PRODUCT_END_PREFIX);
    }

    private static boolean isEmpty(String orders) {
        return orders.trim().isEmpty();
    }
}
