package store.fixture;

import store.model.entity.Product;

public class ProductFixture {
    public static Product promotionProduct =
            new Product("이름", 1000, 10, PromotionFixture.promotion1plus1);
    public static Product noPromotionProduct =
            new Product("이름2", 1000, 10, null);
}
