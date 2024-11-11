package store.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.controller.dto.ProductOrderInfo;
import store.controller.dto.PromotionProductInfo;
import store.controller.dto.ReceiptProductInfo;
import store.fixture.ProductFixture;
import store.fixture.ProductOrderInfoFixture;

class ReceiptServiceTest {
    private final ReceiptService receiptService = new ReceiptService();

    @Test
    @DisplayName("영수증 프로모션 정보를 가져올 수 있다")
    void 영수증_프로모션_정보를_가져올_수_있다() {
        //given
        List<ProductOrderInfo> orderInfos = new ArrayList<>();
        orderInfos.add(new ProductOrderInfo(ProductFixture.promotionProductA, 4, true));
        orderInfos.add(new ProductOrderInfo(ProductFixture.promotionProductA, 3, false));
        orderInfos.add(new ProductOrderInfo(ProductFixture.noPromotionProductB, 3, false));

        //when
        List<PromotionProductInfo> productInfos = receiptService.generatePromotionProductForReceipt(orderInfos)
                .products();

        //then
        assertThat(productInfos).hasSize(1);
        PromotionProductInfo promotionInfo = productInfos.get(0);
        assertThat(promotionInfo.name())
                .isEqualTo(ProductFixture.promotionProductA.getName());
        assertThat(promotionInfo.price()).isEqualTo(ProductFixture.promotionProductA.getPrice() * 2);
        assertThat(promotionInfo.quantity()).isEqualTo(2);
    }

    @Test
    @DisplayName("영수증 상품 정보를 가져올 수 있다")
    void 영수증_상품_정보를_가져올_수_있다() {
        //given
        List<ProductOrderInfo> orderInfos = ProductOrderInfoFixture.productOrderInfoList();
        int product1TotalQuantity = 7;
        int product2TotalQuantity = 3;

        //when
        List<ReceiptProductInfo> products = receiptService.generateOrderProductForReceipt(orderInfos)
                .products();

        //then
        ReceiptProductInfo product1ReceiptInfo = new ReceiptProductInfo(ProductFixture.promotionProductA.getName(),
                product1TotalQuantity,
                ProductFixture.promotionProductA.getPrice() * product1TotalQuantity);
        ReceiptProductInfo product2ReceiptInfo = new ReceiptProductInfo(ProductFixture.noPromotionProductB.getName(),
                product2TotalQuantity,
                ProductFixture.noPromotionProductB.getPrice() * product2TotalQuantity);

        assertThat(products).hasSize(2);
        assertThat(products).containsExactlyInAnyOrder(product1ReceiptInfo, product2ReceiptInfo);
    }
}
