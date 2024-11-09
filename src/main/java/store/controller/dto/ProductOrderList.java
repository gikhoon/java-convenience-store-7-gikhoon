package store.controller.dto;

import java.util.List;

public class ProductOrderList {
    private List<ProductOrderInfo> productOrderList;

    public ProductOrderList(List<ProductOrderInfo> productOrderList) {
        this.productOrderList = productOrderList;
    }

    public List<ProductOrderInfo> getProductOrderList() {
        return productOrderList;
    }
}
