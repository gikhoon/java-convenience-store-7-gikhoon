package store.controller;

import store.controller.dto.ProductOrderList;
import store.model.ProductService;
import store.util.YesOrNoParser;
import store.view.InputView;
import store.view.OutputView;

public class MainController {
    private final InitConvenienceStoreController initController = new InitConvenienceStoreController();
    private final ProductOrderController productOrderController = new ProductOrderController();
    private final ProductService productService = new ProductService();
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    public void run() {
        initController.initPromotions();
        initController.initProducts();
        buy();
    }

    private void buy() {
        do {
            printProductInfo();
            ProductOrderList buyProducts = productOrderController.orderProduct();
            outputView.printReorder();
        } while (YesOrNoParser.parseYesOrNo(inputView.inputYesOrNo()));
    }

    private void printProductInfo() {
        outputView.welcomeMessage();
        outputView.printProducts(
                productService.getAllProductInfos());
    }
}
