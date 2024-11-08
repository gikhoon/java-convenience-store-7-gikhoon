package store.model;

import java.util.List;
import store.controller.dto.ProductInfoDto;
import store.model.entity.Product;
import store.model.repository.ProductRepository;

public class ProductService {
    private static final ProductRepository productRepository = new ProductRepository();

    public ProductInfoDto getAllProductInfos() {
        List<Product> products = productRepository.findAll();
        return ProductInfoDto.toDto(products);
    }
}
