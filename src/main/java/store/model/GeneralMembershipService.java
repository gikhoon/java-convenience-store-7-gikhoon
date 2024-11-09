package store.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import store.controller.dto.ProductOrderInfo;

public class GeneralMembershipService implements MembershipService {
    private static final Double DISCOUNT_RATE = 0.3;

    @Override
    public int calculateDisCount(List<ProductOrderInfo> productOrderList) {
        int totalCost = 0;
        Map<String, List<ProductOrderInfo>> productMap = groupByProductName(productOrderList);
        for (List<ProductOrderInfo> productInfos : productMap.values()) {
            if (hasPromotionProduct(productInfos)) {
                continue;
            }
            totalCost += calculateCost(productInfos);
        }
        return (int) Math.round(totalCost * DISCOUNT_RATE);
    }

    private int calculateCost(List<ProductOrderInfo> productInfos) {
        return productInfos.stream()
                .mapToInt(info -> info.getProduct().getPrice() * info.getQuantity())
                .sum();
    }

    private Map<String, List<ProductOrderInfo>> groupByProductName(List<ProductOrderInfo> productOrderList) {
        return productOrderList.stream()
                .collect(Collectors.groupingBy(info ->
                        info.getProduct().getName())
                );
    }

    private boolean hasPromotionProduct(List<ProductOrderInfo> productInfos) {
        return productInfos.stream()
                .anyMatch(ProductOrderInfo::isPromote);
    }
}
