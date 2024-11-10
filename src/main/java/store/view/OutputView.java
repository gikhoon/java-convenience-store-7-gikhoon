package store.view;

import static store.constant.FormatConstant.EXTRA_ORDER_FORMAT;
import static store.constant.FormatConstant.RECEIPT_INIT_PRICE_FORMAT;
import static store.constant.FormatConstant.RECEIPT_MEMBERSHIP_DISCOUNT;
import static store.constant.FormatConstant.RECEIPT_PROMOTION_DISCOUNT;
import static store.constant.FormatConstant.RECEIPT_TOTAL_COST_DISCOUNT;
import static store.constant.FormatConstant.REMAIN_ORDER_FORMAT;
import static store.constant.ViewMessageConstant.MEMBERSHIP_MESSAGE;
import static store.constant.ViewMessageConstant.RECEIPT_INTRO_MESSAGE;
import static store.constant.ViewMessageConstant.RECEIPT_PRODUCT_ORDER_MESSAGE;
import static store.constant.ViewMessageConstant.RECEIPT_PROMOTION_INTRO_MESSAGE;
import static store.constant.ViewMessageConstant.RECEIPT_RESULT_INTRO_MESSAGE;
import static store.constant.ViewMessageConstant.REORDER_MESSAGE;
import static store.constant.ViewMessageConstant.WELCOME_MESSAGE;

import store.controller.dto.ProductInfoDto;
import store.controller.dto.ReceiptDto;

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

    public void printReceipt(ReceiptDto receipt) {
        System.out.println(RECEIPT_INTRO_MESSAGE);
        printProductInfo(receipt);
        printPromotionInfo(receipt);
        printResultInfo(receipt);
    }

    private void printPromotionInfo(ReceiptDto receipt) {
        System.out.println(RECEIPT_PROMOTION_INTRO_MESSAGE);
        receipt.promotionProduct()
                .products()
                .forEach(System.out::println);
    }

    private void printProductInfo(ReceiptDto receipt) {
        System.out.println(RECEIPT_PRODUCT_ORDER_MESSAGE);
        receipt.orderProduct()
                .products()
                .forEach(System.out::println);
    }

    private void printResultInfo(ReceiptDto receipt) {
        System.out.println(RECEIPT_RESULT_INTRO_MESSAGE);
        int initPrice = receipt.calculateInitPrice();
        int promotionDiscount = receipt.calculateTotalPromotionDiscount();
        int membershipDiscount = receipt.membershipDiscount()
                .discount();
        printInitPriceResult(receipt, initPrice);
        printPromotionResult(promotionDiscount);
        printMembershipResult(membershipDiscount);
        printTotalCostResult(initPrice - promotionDiscount - membershipDiscount);
    }

    private void printTotalCostResult(int cost) {
        System.out.println(String.format(
                RECEIPT_TOTAL_COST_DISCOUNT, cost)
        );
    }

    private void printInitPriceResult(ReceiptDto receipt, int initPrice) {
        System.out.println(String.format(
                RECEIPT_INIT_PRICE_FORMAT, receipt.calculateTotalBuyCount(), initPrice)
        );
    }

    private void printPromotionResult(int promotionDiscount) {
        System.out.println(String.format(
                RECEIPT_PROMOTION_DISCOUNT, promotionDiscount)
        );
    }

    private void printMembershipResult(int membershipDiscount) {
        System.out.println(String.format(
                RECEIPT_MEMBERSHIP_DISCOUNT, membershipDiscount)
        );
    }
}
