package store.fixture;

import java.time.LocalDate;
import store.model.entity.Promotion;

public class PromotionFixture {
    public static Promotion promotion1plus1 =
            new Promotion("프로모션",1, 1,
                    LocalDate.of(2024, 1, 1),
                    LocalDate.of(2124, 12, 31));

    public static Promotion promotion2plus1 =
            new Promotion("프로모션2",2, 1,
                    LocalDate.of(2024, 1, 1),
                    LocalDate.of(2124, 12, 31));
}
