package store.model.entity;

import static store.constant.FormatConstant.QUANTITY_FORMAT;

public class ProductQuantity {
    private Integer quantity;

    public ProductQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format(
                QUANTITY_FORMAT,
                quantity
        );
    }
}
