package store.model;

import java.util.List;
import store.constant.OrderProductConstant;
import store.controller.dto.OrderNameInfo;
import store.controller.dto.ProductInfoDto;
import store.model.entity.Product;
import store.model.repository.ProductRepository;

public class ProductService {
    private static final ProductRepository productRepository = new ProductRepository();

    public ProductInfoDto getAllProductInfos() {
        List<Product> products = productRepository.findAll();
        return ProductInfoDto.toDto(products);
    }

    public OrderNameInfo mapToProductOrderInfo(String data) {
        data = removePrefixSuffix(data);
        String[] splitData = data.split(OrderProductConstant.PRODUCT_SPLIT_SEPARATOR);
        return new OrderNameInfo(splitData[0], Integer.parseInt(splitData[1]));
    }

    private String removePrefixSuffix(String data) {
        return data.replace(OrderProductConstant.PRODUCT_PREFIX, "")
                .replace(OrderProductConstant.PRODUCT_SUFFIX, "");
    }
}
