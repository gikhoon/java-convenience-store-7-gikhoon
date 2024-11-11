package store.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.controller.dto.ProductOrderInfo;
import store.fixture.ProductFixture;
import store.fixture.ProductOrderInfoFixture;

class GeneralMembershipServiceTest {
    private static final Double DISCOUNT_RATE = 0.3;
    private static final Integer MAX_DISCOUNT = 8000;

    private final GeneralMembershipService service = new GeneralMembershipService();

    @Test
    @DisplayName("할인 금액을 계산할 수 있다")
    void 할인_금액을_계산할_수_있다() {
        //given
        List<ProductOrderInfo> orderInfos = ProductOrderInfoFixture.productOrderInfoList();
        int noPromotionQuantity = 3;
        int noPromotionPrice = ProductFixture.noPromotionProductB.getPrice() * noPromotionQuantity;

        //when
        int disCount = service.calculateDisCount(orderInfos);

        //then
        assertThat(disCount).isEqualTo((int) (noPromotionPrice * DISCOUNT_RATE));
    }

    @Test
    @DisplayName("할인 금액이 최대 금액을 넘길 수 없다")
    void 할인_금액이_최대_금액을_넘길_수_없다() {
        //given
        List<ProductOrderInfo> orderInfos = ProductOrderInfoFixture.productOrderInfoList2();

        //when
        int disCount = service.calculateDisCount(orderInfos);

        //then
        assertThat(disCount).isEqualTo(MAX_DISCOUNT);
    }
}