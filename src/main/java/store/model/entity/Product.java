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

    public Product(String name, int price) {
        this.name = name;
        this.price = new ProductPrice(price);
        this.quantity = new ProductQuantity(0);
        this.promotion = null;
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

    public int getPromotionBuy() {
        if (promotion == null) {
            return 0;
        }
        return promotion.getBuy();
    }

    public int getPromotionGet() {
        if (promotion == null) {
            return 0;
        }
        return promotion.getGet();
    }

    public boolean isPromote() {
        return promotion != null && promotion.isPromote();
    }

    public boolean isActive() {
        if (promotion == null) {
            return true;
        }
        return promotion.isPromote();
    }

    public void buy(Integer amount) {
        quantity.decreaseQuantity(amount);
    }

    public int countMoreProduct(int amount) {
        Integer get = promotion.getGet();
        if (promotion.canMoreGet(amount) && this.quantity.isSufficient(amount + get)) {
            return get;
        }
        return 0;
    }

    public int countRemainProduct(Integer amount) {
        int groupSize = promotion.getGet() + promotion.getBuy();
        int promotionGroup = Math.min(amount / groupSize, quantity.getQuantity() / groupSize);
        return amount - promotionGroup * groupSize;
    }
}
