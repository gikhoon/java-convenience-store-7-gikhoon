package store.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import store.constant.OrderProductConstant;
import store.controller.dto.OrderNameInfo;
import store.controller.dto.OrderProduct;
import store.controller.dto.MembershipDiscount;
import store.controller.dto.ProductInfoDto;
import store.controller.dto.ProductOrderInfo;
import store.controller.dto.PromotionProduct;
import store.controller.dto.PromotionProductInfo;
import store.controller.dto.ReceiptProductInfo;
import store.exception.ErrorCode;
import store.model.entity.Product;
import store.model.repository.ProductRepository;

public class ProductService {
    private final ProductRepository productRepository = new ProductRepository();

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
            Product promotionProduct = filterPromoteProduction(products);
            int remainingQuantity = info.getQuantity();
            if (promotionProduct != null) {
                remainingQuantity = deductFromPromotionProduct(promotionProduct, remainingQuantity);
            }
            if (remainingQuantity > 0) {
                filterGeneralProduction(products).buy(remainingQuantity);
            }
        }
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
        validateSufficientProductQuantity(activeProduct, quantity);
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

    public OrderProduct generateOrderProductForReceipt(List<ProductOrderInfo> buyProducts) {
        Map<String, List<ProductOrderInfo>> groupByProductName = groupByProductName(buyProducts);
        return calculateOrderSum(groupByProductName);
    }

    private OrderProduct calculateOrderSum(Map<String, List<ProductOrderInfo>> groupByProductName) {
        List<ReceiptProductInfo> productInfos = groupByProductName.entrySet().stream()
                .map(this::createProductInfo)
                .toList();
        return new OrderProduct(productInfos);
    }

    private ReceiptProductInfo createProductInfo(Map.Entry<String, List<ProductOrderInfo>> entry) {
        String productName = entry.getKey();
        List<ProductOrderInfo> productOrderInfos = entry.getValue();

        int totalQuantity = calculateTotalQuantity(productOrderInfos);
        int totalPrice = calculateTotalPrice(productOrderInfos);

        return new ReceiptProductInfo(productName, totalQuantity, totalPrice);
    }

    private int calculateTotalQuantity(List<ProductOrderInfo> orderInfos) {
        return orderInfos.stream()
                .mapToInt(ProductOrderInfo::getQuantity)
                .sum();
    }

    private int calculateTotalPrice(List<ProductOrderInfo> orderInfos) {
        return orderInfos.stream()
                .mapToInt(ProductOrderInfo::calculateTotalPrice)
                .sum();
    }

    private Map<String, List<ProductOrderInfo>> groupByProductName(List<ProductOrderInfo> productOrderList) {
        return productOrderList.stream()
                .collect(Collectors.groupingBy(info ->
                        info.getProduct().getName())
                );
    }

    public PromotionProduct generatePromotionProductForReceipt(List<ProductOrderInfo> productOrderList) {
        List<PromotionProductInfo> promotionProductInfos = productOrderList.stream()
                .filter(ProductOrderInfo::isPromote)
                .map(PromotionProductInfo::from)
                .toList();
        return new PromotionProduct(promotionProductInfos);
    }
}
