package store.controller;

import store.view.OutputView;

public class MainController {
    private final InitConvenienceStoreController initController = new InitConvenienceStoreController();
    private final OutputView outputView = new OutputView();

    public void run() {
        initController.initPromotions();
        initController.initProducts();
        printProductInfo();
    }

    private void printProductInfo() {
        outputView.welcomeMessage();
    }
}
