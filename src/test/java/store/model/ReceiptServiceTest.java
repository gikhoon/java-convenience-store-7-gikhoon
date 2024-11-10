package store.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import store.controller.dto.ProductOrderInfo;
import store.controller.dto.PromotionProductInfo;
import store.controller.dto.ReceiptProductInfo;
import store.fixture.ProductFixture;

class ReceiptServiceTest {
    private final ReceiptService receiptService = new ReceiptService();

    @Test
    void 영수증_프로모션_정보를_가져올_수_있다() {
        //given
        List<ProductOrderInfo> orderInfos = new ArrayList<>();
        orderInfos.add(new ProductOrderInfo(ProductFixture.promotionProduct, 4, true));
        orderInfos.add(new ProductOrderInfo(ProductFixture.promotionProduct, 3, false));
        orderInfos.add(new ProductOrderInfo(ProductFixture.noPromotionProduct, 3, false));

        //when
        List<PromotionProductInfo> productInfos = receiptService.generatePromotionProductForReceipt(orderInfos)
                .products();

        //then
        assertThat(productInfos).hasSize(1);
        PromotionProductInfo promotionInfo = productInfos.get(0);
        assertThat(promotionInfo.name())
                .isEqualTo(ProductFixture.promotionProduct.getName());
        assertThat(promotionInfo.price()).isEqualTo(ProductFixture.promotionProduct.getPrice() * 2);
        assertThat(promotionInfo.quantity()).isEqualTo(2);
    }

    @Test
    void 영수증_상품_정보를_가져올_수_있다() {
        //given
        int product1Quantity1 = 4;
        int product1Quantity2 = 3;
        int product2Quantity1 = 3;
        List<ProductOrderInfo> orderInfos = new ArrayList<>();
        orderInfos.add(new ProductOrderInfo(ProductFixture.promotionProduct, product1Quantity1, true));
        orderInfos.add(new ProductOrderInfo(ProductFixture.promotionProduct, product1Quantity2, false));
        orderInfos.add(new ProductOrderInfo(ProductFixture.noPromotionProduct, product2Quantity1, false));

        //when
        List<ReceiptProductInfo> products = receiptService.generateOrderProductForReceipt(orderInfos)
                .products();

        //then
        int product1TotalQuantity = product1Quantity1 + product2Quantity1;

        ReceiptProductInfo product1ReceiptInfo = new ReceiptProductInfo(ProductFixture.promotionProduct.getName(),
                product1TotalQuantity,
                ProductFixture.promotionProduct.getPrice() * product1TotalQuantity);
        ReceiptProductInfo product2ReceiptInfo = new ReceiptProductInfo(ProductFixture.noPromotionProduct.getName(),
                product2Quantity1,
                ProductFixture.noPromotionProduct.getPrice() * product2Quantity1);

        assertThat(products).hasSize(2);
        assertThat(products).containsExactlyInAnyOrder(product1ReceiptInfo, product2ReceiptInfo);
    }
}