package store.model;

import java.util.List;
import store.controller.dto.OrderNameInfo;
import store.controller.dto.ProductInfoDto;
import store.controller.dto.ProductOrderInfo;
import store.exception.ErrorCode;
import store.model.entity.Product;
import store.model.repository.ProductRepository;
import store.validation.OrderProductValidator;

public class ProductService {
    private final ProductRepository productRepository = new ProductRepository();
    private final OrderProductValidator orderProductValidator = new OrderProductValidator();

    public ProductInfoDto getAllProductInfos() {
        List<Product> products = productRepository.findAll();
        return ProductInfoDto.toDto(products);
    }

    private List<Product> findActiveProduct(String productName) {
        List<Product> activeProduct = productRepository.findAllByName(productName).stream()
                .filter(Product::isActive)
                .toList();
        if (activeProduct.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.NO_PRODUCT_EXIST_ERROR.getMessage());
        }
        return activeProduct;
    }

    public void buyProduct(List<OrderNameInfo> infos) {
        for (OrderNameInfo info : infos) {
            List<Product> products = findActiveProduct(info.getProductName());
            int remainingQuantity = handlePromotionProduct(products, info.getQuantity());
            if (remainingQuantity > 0) {
                handleGeneralProduct(products, remainingQuantity);
            }
        }
    }

    private int handlePromotionProduct(List<Product> products, int quantity) {
        Product promotionProduct = filterPromoteProduction(products);
        if (promotionProduct != null) {
            return deductFromPromotionProduct(promotionProduct, quantity);
        }
        return quantity;
    }

    private void handleGeneralProduct(List<Product> products, int remainingQuantity) {
        filterGeneralProduction(products).buy(remainingQuantity);
    }

    private int deductFromPromotionProduct(Product promotionProduct, int quantity) {
        if (promotionProduct != null && promotionProduct.getQuantity() > 0) {
            int quantityToDeduct = Math.min(promotionProduct.getQuantity(), quantity);
            promotionProduct.buy(quantityToDeduct);
            return quantity - quantityToDeduct;
        }
        return quantity;
    }

    private Product filterPromoteProduction(List<Product> activeProduct) {
        return activeProduct.stream()
                .filter(Product::isPromote)
                .findFirst()
                .orElse(null);
    }

    private Product filterGeneralProduction(List<Product> activeProduct) {
        return activeProduct.stream()
                .filter(product -> !product.isPromote())
                .findFirst()
                .orElse(null);
    }

    public boolean isPromotionProductExist(String productName) {
        List<Product> activeProduct = findActiveProduct(productName);
        Product product = filterPromoteProduction(activeProduct);
        return product != null;
    }

    public void checkSufficientQuantity(String name, Integer quantity) {
        List<Product> activeProduct = findActiveProduct(name);
        orderProductValidator.validateSufficientProductQuantity(activeProduct, quantity);
    }

    public int countExtraProduct(String productName, Integer quantity) {
        if (!isPromotionProductExist(productName)) {
            return 0;
        }
        List<Product> activeProduct = findActiveProduct(productName);
        Product product = filterPromoteProduction(activeProduct);
        return product.countMoreProduct(quantity);
    }

    public int countRemainProduct(String productName, Integer quantity) {
        List<Product> activeProduct = findActiveProduct(productName);
        Product product = filterPromoteProduction(activeProduct);
        return product.countRemainProduct(quantity);
    }

    public ProductOrderInfo makeProductOrderInfo(String productName, int quantity, boolean isPromote) {
        Product product = productRepository.findByNameAndIsPromote(productName, isPromote);
        return new ProductOrderInfo(product, quantity, isPromote);
    }
}
