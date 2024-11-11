package store.fixture;

import java.util.List;
import store.model.entity.Product;
import store.model.entity.Promotion;

public class ProductFixture {
    public static final Product promotionProductA =
            new Product("A", 1000, 9, PromotionFixture.promotion1plus1);
    public static final Product noPromotionProductA =
            new Product("A", 1000, 10, null);
    public static final Product noPromotionProductB =
            new Product("B", 1000, 10, null);

    public static final List<Promotion> initPromotion = List.of(PromotionFixture.promotion1plus1,
            PromotionFixture.promotion2plus1);
    public static final List<Product> initProduct = List.of(ProductFixture.noPromotionProductB,
            ProductFixture.promotionProductA,
            ProductFixture.noPromotionProductA);
}
