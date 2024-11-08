package store.model.entity;

public class Product {
    private final String name;
    private final Integer price;
    private Integer quantity;
    private final Promotion promotion;

    public Product(String name, Integer price, Integer quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }
}
