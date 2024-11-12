package store.controller.dto;

import static store.constant.FormatConstant.RECEIPT_PROMOTION_FORMAT;

public record PromotionProductInfo(
        String name,
        Integer quantity,
        Integer price
) {
    public static PromotionProductInfo from(ProductOrderInfo orderInfo) {
        int freeProductAmount = orderInfo.getGetAmount();
        return new PromotionProductInfo(
                orderInfo.getProductName(),
                freeProductAmount,
                orderInfo.calculatePromotionDiscountAmount(freeProductAmount)
        );
    }

    @Override
    public String toString() {
        return String.format(RECEIPT_PROMOTION_FORMAT,
                name, quantity);
    }
}
