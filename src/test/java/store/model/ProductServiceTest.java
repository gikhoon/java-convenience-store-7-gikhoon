package store.model;

import static camp.nextstep.edu.missionutils.test.Assertions.assertNowTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.fixture.ProductFixture.*;
import static store.fixture.ProductFixture.initProduct;
import static store.fixture.ProductFixture.initPromotion;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.controller.dto.OrderNameInfo;
import store.controller.dto.ProductInfoDto;
import store.controller.dto.ProductOrderInfo;
import store.model.entity.Product;
import store.model.repository.ProductRepository;
import store.model.repository.PromotionRepository;

class ProductServiceTest {
    private final ProductService productService = new ProductService();
    private final ProductRepository productRepository = new ProductRepository();
    private final PromotionRepository promotionRepository = new PromotionRepository();

    @BeforeEach
    void setUp() {
        promotionRepository.saveAll(initPromotion);
        productRepository.saveAll(initProduct);
    }

    @AfterEach
    void deleteAll() {
        productRepository.clearAll();
        promotionRepository.clearAll();
    }

    @Test
    @DisplayName("창고에 저장된 상품목록을 반환할 수 있다")
    void 창고에_저장된_상품목록을_반환할_수_있다() {
        //given
        int totalProductSize = initProduct.size();

        //when
        ProductInfoDto allProductInfos = productService.getAllProductInfos();

        //then
        assertThat(allProductInfos.productInfo()).hasSize(totalProductSize);
    }

    @Test
    @DisplayName("상품을 구매할 수 있다")
    void 상품을_구매할_수_있다() {
        //given
        String productName = promotionProductA.getName();
        int quantity = 15; //프로모션, 일반 quantity는 9개, 10개
        List<OrderNameInfo> orderNameInfo = List.of(new OrderNameInfo(productName, quantity));

        //when
        productService.buyProduct(orderNameInfo);

        //then
        Product promotionProduct = productRepository.findByNameAndIsPromote(productName, true);
        Product noPromotionProduct = productRepository.findByNameAndIsPromote(productName, false);

        assertThat(promotionProduct.getQuantity()).isEqualTo(0);
        assertThat(noPromotionProduct.getQuantity()).isEqualTo(4);

    }

    @Test
    @DisplayName("프로모션 기간인 상품이 있으면 참을 반환한다")
    void 프로모션_기간인_상품이_있으면_참을_반환한다() {
        //given
        String promotionProductName = promotionProductA.getName();

        assertNowTest(() -> {
            boolean status = productService.isPromotionProductExist(promotionProductName);
            assertThat(status).isTrue();
        }, LocalDate.of(2024, 11, 1).atStartOfDay());
    }

    @Test
    @DisplayName("프로모션 기간인 상품이 없으면 거짓을 반환한다")
    void 프로모션_기간인_상품이_있지_않으면_거짓을_반환한다() {
        //given
        String promotionProductName = promotionProductA.getName();

        //then
        assertNowTest(() -> {
            boolean status = productService.isPromotionProductExist(promotionProductName);
            assertThat(status).isFalse();
        }, LocalDate.of(2021, 1, 1).atStartOfDay());
    }

    @Test
    @DisplayName("상품의 수량이 충분하면 예외를 발생하지 않는다")
    void 상품의_수량이_충분하면_예외를_발생하지_않는다() {
        //given
        String promotionProductName = promotionProductA.getName();
        int totalQuantity = promotionProductA.getQuantity() + noPromotionProductA.getQuantity();

        //then
        assertThatCode(() -> productService.checkSufficientQuantity(promotionProductName, totalQuantity))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("상품의 수량이 충분하지 않으면 예외를 발생한다")
    void 상품의_수량이_충분하지_않으면_예외를_발생한다() {
        //given
        String promotionProductName = promotionProductA.getName();
        int totalQuantity = promotionProductA.getQuantity() + noPromotionProductA.getQuantity();

        //then
        assertThatThrownBy(() -> productService.checkSufficientQuantity(promotionProductName, totalQuantity + 2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("공짜로 가져올 수 있는 상품의 갯수를 반환할 수 있다")
    void 공짜로_가져올_수_있는_상품의_갯수를_반환할_수_있다() {
        //given
        String name = promotionProductA.getName();
        int buy = promotionProductA.getPromotionBuy();
        int get = promotionProductA.getPromotionGet();

        //when
        int extraProduct = productService.countExtraProduct(name, buy);

        //then
        assertThat(extraProduct).isEqualTo(get);
    }

    @Test
    @DisplayName("공짜로 가져올 수 있는 상품의 수량이 부족하면 0을 반환한다")
    void 공짜로_가져올_수_있는_상품의_수량이_부족하면_0을_반환한다() {
        //given
        String name = promotionProductA.getName();
        int buyAmount = 9;

        //when
        int extraProduct = productService.countExtraProduct(name, buyAmount);

        //then
        assertThat(extraProduct).isEqualTo(0);
    }

    @Test
    @DisplayName("공짜로 가져올 수 있는 상품이 없으면 0을 반환한다")
    void 공짜로_가져올_수_있는_상품이_없으면_0을_반환한다() {
        //given
        String name = promotionProductA.getName();
        int buyAmount = promotionProductA.getPromotionBuy() + promotionProductA.getPromotionGet();

        //when
        int extraProduct = productService.countExtraProduct(name, buyAmount);

        //then
        assertThat(extraProduct).isEqualTo(0);
    }

    @Test
    @DisplayName("프로모션 적용이 안되는 상품 수를 반환한다")
    void 프로모션_적용이_안되는_상품_수를_반환한다() {
        //given
        String name = promotionProductA.getName();
        int buyAmount = 10; //10개 구매, 제고는 9개

        //when
        int remainAmount = productService.countRemainProduct(name, buyAmount);

        //then
        assertThat(remainAmount).isEqualTo(2);
    }

    @Test
    @DisplayName("상품 주문 목록을 만들 수 있다")
    void 상품_주문_목록을_만들_수_있다() {
        //given
        String productName = promotionProductA.getName();
        boolean isPromotion = true;
        int quantity = 10;

        //when
        ProductOrderInfo productOrderInfo = productService.makeProductOrderInfo(productName, quantity, isPromotion);

        //then
        assertThat(productOrderInfo.getProductName())
                .isEqualTo(productName);
        assertThat(productOrderInfo.getQuantity())
                .isEqualTo(quantity);
        assertThat(productOrderInfo.getQuantity())
                .isEqualTo(quantity);
        assertThat(productOrderInfo.getProduct().getPromotion())
                .isEqualTo(promotionProductA.getPromotion());
    }
}
