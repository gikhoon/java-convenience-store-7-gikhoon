package store.controller;

import java.util.List;
import store.controller.dto.OrderNameInfo;
import store.controller.dto.ProductOrderList;
import store.model.ProductService;
import store.util.OrderProductParser;
import store.validation.OrderProductValidator;
import store.view.InputView;
import store.view.OutputView;

public class ProductOrderController {
    private final ProductService productService = new ProductService();
    private final OutputView outputView = new OutputView();
    private final InputView inputView = new InputView();

    public void orderProduct() {
        while (true) {
            try {
                List<String> orders = inputProductOrder();
                processOrder(orders);
                break;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private ProductOrderList processOrder(List<String> orders) {
        List<OrderNameInfo> orderNameInfos = orders.stream()
                .map(productService::mapToProductOrderInfo)
                .toList();
        ProductOrderList productOrderList = productService.generateProductOrderList(orderNameInfos);
        return null;
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
