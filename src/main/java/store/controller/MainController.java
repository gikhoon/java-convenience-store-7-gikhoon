package store.controller;

public class MainController {
    private final InitConvenienceStoreController initController = new InitConvenienceStoreController();

    public void run() {
        initConvenienceStore();
    }

    private void initConvenienceStore() {
        initController.initProducts();
        initController.initPromotions();
    }
}
