package store.controller;

import store.model.ProductService;
import store.view.OutputView;

public class MainController {
    private final InitConvenienceStoreController initController = new InitConvenienceStoreController();
    private final ProductOrderController productOrderController = new ProductOrderController();
    private final ProductService productService = new ProductService();
    private final OutputView outputView = new OutputView();

    public void run() {
        initController.initPromotions();
        initController.initProducts();
        printProductInfo();
        productOrderController.orderProduct();
    }

    private void printProductInfo() {
        outputView.welcomeMessage();
        outputView.printProducts(
                productService.getAllProductInfos());
    }
}
