package store.model.repository;

import java.util.ArrayList;
import java.util.List;
import store.model.entity.Product;

public class ProductRepository {
    private List<Product> products = new ArrayList<>();

    public void saveAll(List<Product> product) {
        products.addAll(product);
    }
}
