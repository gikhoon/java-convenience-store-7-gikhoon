package store.model;

import java.util.List;
import store.controller.dto.ProductInfo;
import store.controller.dto.ProductInfoDto;
import store.model.repository.ProductRepository;

public class ProductService {
    private static final ProductRepository productRepository = new ProductRepository();

    public ProductInfoDto getAllProductInfos() {
        List<ProductInfo> productInfos = productRepository.findAll().stream()
                .map(ProductInfo::from)
                .toList();

        return new ProductInfoDto(productInfos);
    }
}
