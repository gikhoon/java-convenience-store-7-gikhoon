package store.controller;

import store.controller.dto.OrderProduct;
import store.controller.dto.ProductOrderList;
import store.controller.dto.PromotionProduct;
import store.controller.dto.ReceiptDto;
import store.model.ReceiptService;

public class ReceiptController {
    private final ReceiptService receiptService = new ReceiptService();

    public ReceiptDto generateReceipt(ProductOrderList buyProducts, int membershipDiscount) {
        OrderProduct orderProduct = receiptService.generateOrderProductForReceipt(
                buyProducts.getProductOrderList());
        PromotionProduct promotionProduct = receiptService.generatePromotionProductForReceipt(
                buyProducts.getProductOrderList());
        return ReceiptDto.of(orderProduct, promotionProduct, membershipDiscount);
    }
}
