package store.fixture;

import java.util.ArrayList;
import java.util.List;
import store.controller.dto.ProductOrderInfo;

public class ProductOrderInfoFixture {
    public static List<ProductOrderInfo> productOrderInfoList() {
        List<ProductOrderInfo> orderInfos = new ArrayList<>();
        orderInfos.add(new ProductOrderInfo(ProductFixture.promotionProduct, 3, true));
        orderInfos.add(new ProductOrderInfo(ProductFixture.promotionProduct, 4, false));
        orderInfos.add(new ProductOrderInfo(ProductFixture.noPromotionProduct, 3, false));
        return orderInfos;
    }

}
