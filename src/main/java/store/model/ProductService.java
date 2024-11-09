package store.model;

import java.util.ArrayList;
import java.util.List;
import store.constant.OrderProductConstant;
import store.controller.dto.OrderNameInfo;
import store.controller.dto.ProductInfoDto;
import store.controller.dto.ProductOrderInfo;
import store.controller.dto.ProductOrderList;
import store.exception.ErrorCode;
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

    public ProductOrderList generateProductOrderList(List<OrderNameInfo> orderNameInfos) {
        List<ProductOrderInfo> orderInfos = new ArrayList<>();
        for (OrderNameInfo orderNameInfo : orderNameInfos) {
            List<Product> activeProduct = findActiveProduct(orderNameInfo.productName());
            orderInfos.addAll(addProductOrderInfo(activeProduct, orderNameInfo.quantity()));
        }
        return new ProductOrderList(orderInfos);
    }

    private List<ProductOrderInfo> addProductOrderInfo(List<Product> activeProduct, Integer quantity) {
        validateSufficientProductQuantity(activeProduct, quantity);

        List<ProductOrderInfo> productOrderInfos = new ArrayList<>();
        int remainingQuantity = quantity;
        remainingQuantity = processProductOrderInfo(activeProduct, true, remainingQuantity, productOrderInfos);
        processProductOrderInfo(activeProduct, false, remainingQuantity, productOrderInfos);
        return productOrderInfos;
    }

    private int processProductOrderInfo(List<Product> products, boolean isPromotion, int remainingQuantity, List<ProductOrderInfo> productOrderInfos) {
        for (Product product : products) {
            if (product.isPromote() == isPromotion && remainingQuantity > 0) {
                int quantityToDeduct = Math.min(product.getQuantity(), remainingQuantity);
                productOrderInfos.add(new ProductOrderInfo(product, quantityToDeduct));
                remainingQuantity -= quantityToDeduct;
            }
        }
        return remainingQuantity;
    }

    private void validateSufficientProductQuantity(List<Product> activeProduct, Integer quantity) {
        int totalQuantity = activeProduct.stream()
                .mapToInt(Product::getQuantity)
                .sum();
        if (quantity > totalQuantity) {
            throw new IllegalArgumentException(ErrorCode.NOT_SUFFICIENT_QUANTITY.getMessage());
        }
    }

    private List<Product> findActiveProduct(String productName) {
        List<Product> activeProduct = productRepository.findAllByName(productName).stream()
                .filter(Product::isPromote)
                .toList();
        if (activeProduct.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.NO_PRODUCT_EXIST_ERROR.getMessage());
        }
        return activeProduct;
    }
}
