package store.controller;

import store.controller.dto.OrderProduct;
import store.controller.dto.ProductOrderList;
import store.controller.dto.PromotionProduct;
import store.controller.dto.ReceiptDto;
import store.model.ProductService;

public class ReceiptController {
    private final ProductService productService = new ProductService();

    public ReceiptDto generateReceipt(ProductOrderList buyProducts, int membershipDiscount) {
        OrderProduct orderProduct = productService.generateOrderProductForReceipt(
                buyProducts.getProductOrderList());
        PromotionProduct promotionProduct = productService.generatePromotionProductForReceipt(
                buyProducts.getProductOrderList());
        return ReceiptDto.of(orderProduct, promotionProduct, membershipDiscount);
    }
}
