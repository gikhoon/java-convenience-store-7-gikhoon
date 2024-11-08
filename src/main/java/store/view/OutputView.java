package store.view;

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
}
