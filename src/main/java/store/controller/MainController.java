package store.controller;

import store.controller.dto.ProductOrderList;
import store.controller.dto.ReceiptDto;
import store.model.ProductService;
import store.util.YesOrNoParser;
import store.view.InputView;
import store.view.OutputView;

public class MainController {
    private final InitConvenienceStoreController initController = new InitConvenienceStoreController();
    private final ProductOrderController productOrderController = new ProductOrderController();
    private final MembershipController membershipController;
    private final ReceiptController receiptController = new ReceiptController();
    private final ProductService productService = new ProductService();
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    public MainController(MembershipController membershipController) {
        this.membershipController = membershipController;
    }

    public void run() {
        initController.initPromotions();
        initController.initProducts();
        buy();
    }

    private void buy() {
        do {
            printProductInfo();
            ProductOrderList buyProducts = productOrderController.orderProduct();
            int membershipDiscount = membershipController.calculateMembershipDisCount(buyProducts.getProductOrderList());
            ReceiptDto receipt = receiptController.generateReceipt(buyProducts, membershipDiscount);
            outputView.printReceipt(receipt);
            outputView.printReorder();
        } while (YesOrNoParser.parseYesOrNo(inputView.inputYesOrNo()));
    }

    private void printProductInfo() {
        outputView.welcomeMessage();
        outputView.printProducts(
                productService.getAllProductInfos());
    }
}
