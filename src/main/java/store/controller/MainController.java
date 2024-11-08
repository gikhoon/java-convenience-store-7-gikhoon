package store.controller;

public class MainController {
    private final InitConvenienceStoreController initController = new InitConvenienceStoreController();

    public void run() {
        initController.initProducts();
        initController.initPromotions();
    }
}
