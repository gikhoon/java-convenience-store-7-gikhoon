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
    private final OrderProductValidator orderProductValidator = new OrderProductValidator();
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
                .map(OrderNameInfo::mapToProductOrderInfo)
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
        int remainingQuantity = orderNameInfo.getQuantity();
        if (isPromotionProductExist(orderNameInfo.getProductName())) {
            extraOrder(orderNameInfo);
            remainingQuantity = remainProduct(orderNameInfo);
        }
        addOrderInfos(orderNameInfo, remainingQuantity, productOrderInfos);
    }

    private void addOrderInfos(OrderNameInfo orderNameInfo, int remainingQuantity, List<ProductOrderInfo> productOrderInfos) {
        addNonPromotionalOrderInfo(orderNameInfo, remainingQuantity, productOrderInfos);
        addPromotionalOrderInfo(orderNameInfo, remainingQuantity, productOrderInfos);
    }

    private void addNonPromotionalOrderInfo(OrderNameInfo orderNameInfo, int remainingPromotionQuantity,
                                            List<ProductOrderInfo> productOrderInfos) {
        if (remainingPromotionQuantity > 0) {
            ProductOrderInfo orderInfo = productService.makeProductOrderInfo(
                    orderNameInfo.getProductName(), remainingPromotionQuantity, false
            );
            productOrderInfos.add(orderInfo);
        }
    }

    private void addPromotionalOrderInfo(OrderNameInfo orderNameInfo, int remainingPromotionQuantity,
                                         List<ProductOrderInfo> productOrderInfos) {
        int promotionalQuantity = orderNameInfo.getQuantity() - remainingPromotionQuantity;
        if (promotionalQuantity > 0) {
            ProductOrderInfo orderInfo = productService.makeProductOrderInfo(
                    orderNameInfo.getProductName(), promotionalQuantity, true
            );
            productOrderInfos.add(orderInfo);
        }
    }

    private int remainProduct(OrderNameInfo orderNameInfo) {
        int remainProduct = productService.countRemainProduct(orderNameInfo.getProductName(),
                orderNameInfo.getQuantity());
        return deleteRemainProductIfPresent(orderNameInfo, remainProduct);
    }

    private int deleteRemainProductIfPresent(OrderNameInfo orderNameInfo, int remainProduct) {
        if (remainProduct > 0) {
            outputView.printRemainProduct(orderNameInfo.getProductName(), remainProduct);
            boolean status = getYesOrNo();
            if (!status) {
                deleteRemainProduct(status, remainProduct, orderNameInfo);
                return 0;
            }
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
            addQuantityInOrderNameInfo(getYesOrNo(), extraProduct, orderNameInfo);
        }
    }

    private void addQuantityInOrderNameInfo(boolean status, int extraProduct, OrderNameInfo orderNameInfo) {
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
            orderProductValidator.validateProductOrderFormat(order.trim());
        }
    }

    private boolean isPromotionProductExist(String productName) {
        return productService.isPromotionProductExist(productName);
    }
}
