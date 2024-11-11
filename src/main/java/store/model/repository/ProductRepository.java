package store.model.repository;

import java.util.ArrayList;
import java.util.List;
import store.model.entity.Product;

public class ProductRepository {
    private static List<Product> products = new ArrayList<>();

    public void saveAll(List<Product> product) {
        products.addAll(product);
    }

    public List<Product> findAll() {
        return products;
    }

    public List<Product> findAllByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .toList();
    }

    public Product findByNameAndIsPromote(String name, boolean isPromote) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .filter(product -> {
                    if (isPromote) {
                        return product.getPromotion() != null;
                    }
                    return product.getPromotion() == null;
                })
                .findFirst()
                .orElse(null);
    }

    public boolean isDataExist() {
        return !products.isEmpty();
    }
}
