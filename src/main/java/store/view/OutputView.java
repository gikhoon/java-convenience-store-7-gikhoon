package store.view;

import static store.constant.FormatConstant.EXTRA_ORDER_FORMAT;
import static store.constant.FormatConstant.REMAIN_ORDER_FORMAT;
import static store.constant.ViewMessageConstant.MEMBERSHIP_MESSAGE;
import static store.constant.ViewMessageConstant.REORDER_MESSAGE;
import static store.constant.ViewMessageConstant.WELCOME_MESSAGE;

import store.controller.dto.ProductInfoDto;

public class OutputView {
    private static final String ERROR_PREFIX = "[ERROR] ";

    public void welcomeMessage() {
        System.out.println(WELCOME_MESSAGE);
    }

    public void printProducts(ProductInfoDto productInfos) {
        productInfos.productInfo().forEach(System.out::println);
    }

    public void printErrorMessage(String message) {
        System.out.println(ERROR_PREFIX + message);
    }

    public void printProductBuy(String productName, Integer quantity) {
        System.out.println(String.format(
                EXTRA_ORDER_FORMAT, productName, quantity
        ));
    }

    public void printRemainProduct(String productName, int quantity) {
        System.out.println(String.format(
                REMAIN_ORDER_FORMAT, productName, quantity
        ));
    }

    public void printReorder() {
        System.out.println(REORDER_MESSAGE);
    }

    public void printMemberShipMessage() {
        System.out.println(MEMBERSHIP_MESSAGE);
    }
}
