package store.controller;

import java.util.ArrayList;
import java.util.List;
import store.controller.dto.OrderNameInfo;
import store.controller.dto.ProductOrderInfo;
import store.controller.dto.ProductOrderList;
import store.model.ProductService;
import store.util.OrderProductParser;
import store.util.YesOrNoParser;
import store.validation.OrderProductValidator;
import store.view.InputView;
import store.view.OutputView;

public class ProductOrderController {
    private final ProductService productService = new ProductService();
    private final OutputView outputView = new OutputView();
    private final InputView inputView = new InputView();

    public ProductOrderList orderProduct() {
        while (true) {
            try {
                List<String> orders = inputProductOrder();
                return processOrder(orders);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private ProductOrderList processOrder(List<String> orders) {
        List<OrderNameInfo> orderNameInfos = orders.stream()
                .map(productService::mapToProductOrderInfo)
                .toList();

        List<ProductOrderInfo> productOrderInfo = orderProductInfo(orderNameInfos);
        productService.buyProduct(orderNameInfos);
        return new ProductOrderList(productOrderInfo);
    }

    private List<ProductOrderInfo> orderProductInfo(List<OrderNameInfo> orderNameInfos) {
        List<ProductOrderInfo> productOrderInfos = new ArrayList<>();
        for (OrderNameInfo orderNameInfo : orderNameInfos) {
            processOrderForProduct(orderNameInfo, productOrderInfos);
        }
        return productOrderInfos;
    }

    private void processOrderForProduct(OrderNameInfo orderNameInfo, List<ProductOrderInfo> productOrderInfos) {
        checkOrderQuantity(orderNameInfo.getProductName(), orderNameInfo.getQuantity());

        int remainingPromotionQuantity = promotionProduct(orderNameInfo);

        addNonPromotionalOrderInfo(orderNameInfo, remainingPromotionQuantity, productOrderInfos);
        addPromotionalOrderInfo(orderNameInfo, remainingPromotionQuantity, productOrderInfos);
    }

    private void addNonPromotionalOrderInfo(OrderNameInfo orderNameInfo, int remainingPromotionQuantity, List<ProductOrderInfo> productOrderInfos) {
        if (remainingPromotionQuantity > 0) {
            ProductOrderInfo orderInfo = productService.makeProductOrderInfo(
                    orderNameInfo.getProductName(), remainingPromotionQuantity, false
            );
            productOrderInfos.add(orderInfo);
        }
    }

    private void addPromotionalOrderInfo(OrderNameInfo orderNameInfo, int remainingPromotionQuantity, List<ProductOrderInfo> productOrderInfos) {
        int promotionalQuantity = orderNameInfo.getQuantity() - remainingPromotionQuantity;
        if (promotionalQuantity > 0) {
            ProductOrderInfo orderInfo = productService.makeProductOrderInfo(
                    orderNameInfo.getProductName(), promotionalQuantity, true
            );
            productOrderInfos.add(orderInfo);
        }
    }

    private int promotionProduct(OrderNameInfo orderNameInfo) {
        if (productService.isPromotionProductExist(orderNameInfo.getProductName())) {
            extraOrder(orderNameInfo);
            return remainProduct(orderNameInfo);
        }
        return orderNameInfo.getQuantity();
    }

    private int remainProduct(OrderNameInfo orderNameInfo) {
        int remainProduct = productService.countRemainProduct(orderNameInfo.getProductName(),
                orderNameInfo.getQuantity());
        if (remainProduct > 0) {
            outputView.printRemainProduct(orderNameInfo.getProductName(), remainProduct);
            deleteRemainProduct(getYesOrNo(), remainProduct, orderNameInfo);
        }
        return remainProduct;
    }

    private void deleteRemainProduct(boolean status, int remainProduct, OrderNameInfo orderNameInfo) {
        if (!status) {
            orderNameInfo.deleteQuantity(remainProduct);
        }
    }

    private void extraOrder(OrderNameInfo orderNameInfo) {
        int extraProduct = productService.countExtraProduct(orderNameInfo.getProductName(),
                orderNameInfo.getQuantity());
        if (extraProduct > 0) {
            outputView.printProductBuy(orderNameInfo.getProductName(), extraProduct);
            addAdditionalProduct(getYesOrNo(), extraProduct, orderNameInfo);
        }
    }

    private void addAdditionalProduct(boolean status, int extraProduct, OrderNameInfo orderNameInfo) {
        if (status) {
            orderNameInfo.addQuantity(extraProduct);
        }
    }

    private boolean getYesOrNo() {
        while (true) {
            try {
                return YesOrNoParser.parseYesOrNo(inputView.inputYesOrNo());
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void checkOrderQuantity(String name, Integer quantity) {
        productService.checkSufficientQuantity(name, quantity);
    }

    private List<String> inputProductOrder() {
        List<String> orders = OrderProductParser.parseOrder(inputView.inputProductOrder());
        validateOrders(orders);
        return orders;
    }

    private void validateOrders(List<String> orders) {
        for (String order : orders) {
            OrderProductValidator.validateProductOrderFormat(order.trim());
        }
    }
}
