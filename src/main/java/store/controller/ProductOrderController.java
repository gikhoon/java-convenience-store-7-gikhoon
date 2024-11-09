package store.controller;

import java.util.List;
import store.util.OrderProductParser;
import store.validation.OrderProductValidator;
import store.view.InputView;
import store.view.OutputView;

public class ProductOrderController {
    private final OutputView outputView = new OutputView();
    private final InputView inputView = new InputView();

    public void orderProduct() {
        while (true) {
            try {
                List<String> orders = inputProductORder();
                break;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
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
