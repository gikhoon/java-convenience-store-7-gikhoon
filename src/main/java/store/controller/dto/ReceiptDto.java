package store.controller.dto;

public record ReceiptDto(
        OrderProduct orderProduct,
        PromotionProduct promotionProduct,
        MembershipDiscount membershipDiscount
) {
    public static ReceiptDto of(OrderProduct orderProduct, PromotionProduct promotionProduct, int membershipDiscount) {
        return new ReceiptDto(orderProduct, promotionProduct, new MembershipDiscount(membershipDiscount));
    }
}
