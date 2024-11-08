package store.model.entity;

import org.assertj.core.util.Strings;

public class Product {
    private final String name;
    private final ProductPrice price;
    private ProductQuantity quantity;
    private final Promotion promotion;

    public Product(String name, Integer price, Integer quantity, Promotion promotion) {
        this.name = name;
        this.price = new ProductPrice(price);
        this.quantity = new ProductQuantity(quantity);
        this.promotion = promotion;
    }

    public int getPrice() {
        return price.getPrice();
    }

    public String getPriceToString() {
        return price.toString();
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity.getQuantity();
    }

    public String getQuantityToString() {
        return quantity.toString();
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public String getPromotionName() {
        if (promotion != null) {
            return promotion.getName();
        }
        return "";
    }
}
