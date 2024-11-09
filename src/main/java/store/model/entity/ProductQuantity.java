package store.model.entity;

import static store.constant.FormatConstant.QUANTITY_FORMAT;

public class ProductQuantity {
    private static final String NO_QUANTITY = "재고 없음";

    private Integer quantity;

    public ProductQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void decreaseQuantity(int amount) {
        quantity -= amount;
    }

    @Override
    public String toString() {
        if (quantity == 0) {
            return NO_QUANTITY;
        }
        return String.format(
                QUANTITY_FORMAT,
                quantity
        );
    }

    public boolean isSufficient(int quantity) {
        return this.quantity >= quantity;
    }
}
