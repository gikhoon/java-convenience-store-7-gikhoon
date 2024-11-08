package store.controller;

public class MainController {
    private final InitConvenienceStoreController initController = new InitConvenienceStoreController();

    public void run() {
        initProducts();
        initPromotions();
    }

    private void initProducts() {
        initController.initProducts();
    }

    private void initPromotions() {
        initController.initPromotions();
    }
}
