package store.controller.dto;

public record ReceiptDto(
        OrderProduct orderProduct,
        PromotionProduct promotionProduct,
        MembershipDiscount membershipDiscount
) {
}
