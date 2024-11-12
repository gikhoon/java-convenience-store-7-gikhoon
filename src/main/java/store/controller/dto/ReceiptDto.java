package store.controller.dto;

public record ReceiptDto(
        OrderProduct orderProduct,
        PromotionProduct promotionProduct,
        MembershipDiscount membershipDiscount
) {
    public static ReceiptDto of(OrderProduct orderProduct, PromotionProduct promotionProduct, int membershipDiscount) {
        return new ReceiptDto(orderProduct, promotionProduct, new MembershipDiscount(membershipDiscount));
    }

    public int calculateTotalPromotionDiscount() {
        return promotionProduct.calculateTotalPromotionDiscount();
    }

    public int calculateTotalBuyCount() {
        return orderProduct.calculateTotalQuantity();
    }

    public int calculateInitPrice() {
        return orderProduct.calculateTotalPrice();
    }
}
