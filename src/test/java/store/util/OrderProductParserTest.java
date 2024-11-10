package store.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderProductParserTest {

    @Test
    @DisplayName("상품 목록 입력 시 쉼표를 기준으로 분리할 수 있다")
    void 상품_목록_입력시_쉼표를_기준으로_분리할_수_있다() {
        //given
        String input = "[햄버거-1],[지우개-2]";

        //when
        List<String> result = OrderProductParser.parseOrder(input);

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).containsExactly("[햄버거-1]", "[지우개-2]");
    }

    @Test
    @DisplayName("상품 목록 입력 시 빈 문자열이면 예외 발생")
    void 상품_목록_입력시_빈_문자열이면_예외_발생() {
        assertThatThrownBy(() -> OrderProductParser.parseOrder("   "))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품 목록 입력 시 대괄호로 끝나지 않으면 예외 발생")
    void 상품_목록_입력시_대괄호로_끝나지_않으면_예외_발생() {
        assertThatThrownBy(() -> OrderProductParser.parseOrder("[햄버거-1],[지우개-2]a"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품 목록 입력 시 대괄호로 시작하지 않으면 예외 발생")
    void 상품_목록_입력시_대괄호로_시작하지_않으면_예외_발생() {
        assertThatThrownBy(() -> OrderProductParser.parseOrder("]햄버거-1],[지우개-2]"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
