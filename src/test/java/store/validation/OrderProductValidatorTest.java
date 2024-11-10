package store.validation;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.entity.Product;

class OrderProductValidatorTest {
    private final OrderProductValidator validator = new OrderProductValidator();

    @DisplayName("구매 상품 입력 시 조건에 맞으면 예외가 발생하지 않는다")
    @Test
    void 구매_상품_입력_시_조건에_맞으면_예외가_발생하지_않는다() {
        assertThatCode(() -> validator.validateProductOrderFormat("[상품-1]"))
                .doesNotThrowAnyException();
    }

    @DisplayName("구매 상품 입력 시 대괄호로 시작하지 않으면 예외 발생")
    @Test
    void 구매_상품_입력_시_대괄호로_시작하지_않으면_예외_발생() {
        assertThatThrownBy(() -> validator.validateProductOrderFormat("a[상품-1]"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("구매 상품 입력 시 대괄호로 끝나지 않으면 예외 발생")
    @Test
    void 구매_상품_입력_시_대괄호로_끝나지_않으면_예외_발생() {
        assertThatThrownBy(() -> validator.validateProductOrderFormat("[상품-1]a"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("구매 상품 입력 시 - 가 없으면 예외 발생")
    @Test
    void 구매_상품_입력_시_구분문자가_없으면_예외_발생() {
        assertThatThrownBy(() -> validator.validateProductOrderFormat("[상품/1]"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("구매 상품 입력 시 총량이 숫자가 아니면 예외 발생")
    @Test
    void 구매_상품_입력_시_총량이_숫자가_아니면_예외_발생() {
        assertThatThrownBy(() -> validator.validateProductOrderFormat("[상품-1a]"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 갯수가 모자라면 예외가 발생한다")
    @Test
    void 상품의_갯수가_모자라면_예외가_발생한다() {
        //given
        int productQuantity1 = 1;
        int productQuantity2 = 2;
        List<Product> products = new ArrayList<>();
        products.add(new Product("이름", 1000, productQuantity1, null));
        products.add(new Product("이름", 1000, productQuantity2, null));

        int wantToBuy = 5;
        assertThatThrownBy(() -> validator.validateSufficientProductQuantity(products, wantToBuy))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 갯수가 모자라지 않으면 예외가 발생하지 않는다")
    @Test
    void 상품의_갯수가_모자르지_않으면_예외가_발생하지_않는다() {
        //given
        int productQuantity1 = 1;
        int productQuantity2 = 2;
        List<Product> products = new ArrayList<>();
        products.add(new Product("이름", 1000, productQuantity1, null));
        products.add(new Product("이름", 1000, productQuantity2, null));

        int wantToBuy = 3;
        assertThatCode(() -> validator.validateSufficientProductQuantity(products, wantToBuy))
                .doesNotThrowAnyException();
    }
}