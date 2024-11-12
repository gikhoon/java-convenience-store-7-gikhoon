package store.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import store.controller.dto.OrderProduct;
import store.controller.dto.ProductOrderInfo;
import store.controller.dto.PromotionProduct;
import store.controller.dto.PromotionProductInfo;
import store.controller.dto.ReceiptProductInfo;

public class ReceiptService {
    public PromotionProduct generatePromotionProductForReceipt(List<ProductOrderInfo> productOrderList) {
        List<PromotionProductInfo> promotionProductInfos = productOrderList.stream()
                .filter(ProductOrderInfo::isPromote)
                .map(PromotionProductInfo::from)
                .toList();
        return new PromotionProduct(promotionProductInfos);
    }

    public OrderProduct generateOrderProductForReceipt(List<ProductOrderInfo> buyProducts) {
        Map<String, List<ProductOrderInfo>> groupByProductName = groupByProductName(buyProducts);
        return calculateOrderSum(groupByProductName);
    }

    private OrderProduct calculateOrderSum(Map<String, List<ProductOrderInfo>> groupByProductName) {
        List<ReceiptProductInfo> productInfos = groupByProductName.entrySet().stream()
                .map(this::createProductInfo)
                .toList();
        return new OrderProduct(productInfos);
    }

    private ReceiptProductInfo createProductInfo(Map.Entry<String, List<ProductOrderInfo>> entry) {
        String productName = entry.getKey();
        List<ProductOrderInfo> productOrderInfos = entry.getValue();

        int totalQuantity = calculateTotalQuantity(productOrderInfos);
        int totalPrice = calculateTotalPrice(productOrderInfos);

        return new ReceiptProductInfo(productName, totalQuantity, totalPrice);
    }

    private int calculateTotalQuantity(List<ProductOrderInfo> orderInfos) {
        return orderInfos.stream()
                .mapToInt(ProductOrderInfo::getQuantity)
                .sum();
    }

    private int calculateTotalPrice(List<ProductOrderInfo> orderInfos) {
        return orderInfos.stream()
                .mapToInt(ProductOrderInfo::calculateTotalPrice)
                .sum();
    }

    private Map<String, List<ProductOrderInfo>> groupByProductName(List<ProductOrderInfo> productOrderList) {
        return productOrderList.stream()
                .collect(Collectors.groupingBy(info ->
                        info.getProduct().getName())
                );
    }
}
