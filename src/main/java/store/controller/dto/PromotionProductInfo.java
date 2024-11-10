package store.controller.dto;

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
}
