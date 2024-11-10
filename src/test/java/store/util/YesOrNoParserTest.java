package store.util;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class YesOrNoParserTest {

    @DisplayName("Y를 입력하면 참이 반환된다")
    @Test
    void Y를_입력하면_참이_반환된다() {
        //when
        boolean status = YesOrNoParser.parseYesOrNo("Y");

        //then
        assertTrue(status);
    }

    @DisplayName("N를 입력하면 참이 반환된다")
    @Test
    void N를_입력하면_참이_반환된다() {
        //when
        boolean status = YesOrNoParser.parseYesOrNo("N");

        //then
        assertFalse(status);
    }

    @DisplayName("Y/N이 아닌 값을 입력하면 예외가 발생한다")
    @Test
    void Y_N이_아닌_값을_입력하면_예외가_발생한다() {
        assertThatThrownBy(() -> YesOrNoParser.parseYesOrNo("A"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}