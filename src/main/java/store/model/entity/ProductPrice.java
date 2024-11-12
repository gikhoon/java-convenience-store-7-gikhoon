package store.model.entity;

import store.constant.FormatConstant;

public class ProductPrice {
    private final Integer price;

    public ProductPrice(int price) {
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format(
                FormatConstant.PRICE_FORMAT,
                price
        );
    }
}
