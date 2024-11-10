package store.controller.dto;

public record PromotionProductInfo(
        String name,
        Integer quantity
) {
    public static PromotionProductInfo from(ProductOrderInfo orderInfo) {
        return new PromotionProductInfo(
                orderInfo.getProductName(),
                orderInfo.getGetAmount()
        );
    }
}
