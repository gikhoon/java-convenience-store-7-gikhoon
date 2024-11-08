package store.controller;

public class MainController {
    private final InitConvenienceStoreController initController = new InitConvenienceStoreController();

    public void run() {
        initController.initPromotions();
        initController.initProducts();
    }
}
