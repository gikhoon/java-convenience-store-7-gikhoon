package store.controller.dto;

public class ReceiptDto {
    private OrderProduct orderProduct;
    private PromotionProduct promotionProduct;
    private PriceData priceData;

    public ReceiptDto(OrderProduct orderProduct, PromotionProduct promotionProduct, PriceData priceData) {
        this.orderProduct = orderProduct;
        this.promotionProduct = promotionProduct;
        this.priceData = priceData;
    }
}
