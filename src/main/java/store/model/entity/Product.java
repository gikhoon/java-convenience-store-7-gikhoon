package store.model.entity;

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
}
