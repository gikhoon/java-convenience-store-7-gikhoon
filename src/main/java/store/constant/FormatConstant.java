package store.constant;

public class FormatConstant {
    public static final String PRICE_FORMAT = "%,d원";
    public static final String QUANTITY_FORMAT = "%,d개";
    public static final String PRODUCT_INFO_FORMAT = "- %s %s %s %s";
    public static final String RECEIPT_PRODUCT_FORMAT = "%-12s %6d  %,8d";
    public static final String RECEIPT_PROMOTION_FORMAT = "%-12s %6d";
    public static final String RECEIPT_INIT_PRICE_FORMAT = "총구매액\t\t%d\t%,d";
    public static final String RECEIPT_PROMOTION_DISCOUNT = "행사할인\t\t-" + "%,d";
    public static final String RECEIPT_MEMBERSHIP_DISCOUNT = "멤버십할인\t\t-" + "%,d";
    public static final String RECEIPT_TOTAL_COST_DISCOUNT = "내실돈\t\t%,d";
    public static final String EXTRA_ORDER_FORMAT = "현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";
    public static final String REMAIN_ORDER_FORMAT = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)";
}
