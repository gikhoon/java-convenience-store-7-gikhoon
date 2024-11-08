package store.controller;

import static store.constant.PromotionFileConstant.PROMOTION_BUY_INDEX;
import static store.constant.PromotionFileConstant.PROMOTION_END_DATE_INDEX;
import static store.constant.PromotionFileConstant.PROMOTION_GET_INDEX;
import static store.constant.PromotionFileConstant.PROMOTION_NAME_INDEX;
import static store.constant.PromotionFileConstant.PROMOTION_START_DATE_INDEX;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import store.constant.ProductFileConstant;
import store.constant.PromotionFileConstant;
import store.exception.ErrorCode;
import store.model.entity.Product;
import store.model.entity.Promotion;
import store.model.repository.ProductRepository;
import store.model.repository.PromotionRepository;
import store.util.ConvenienceStoreInitParser;

public class InitConvenienceStoreController {
    private final PromotionRepository promotionRepository = new PromotionRepository();
    private final ProductRepository productRepository = new ProductRepository();

    public void initProducts() {
        try (BufferedReader reader = makeBufferedReader(ProductFileConstant.PRODUCT_FILE)) {
            List<String> productData = reader.lines().skip(1).toList();
            productRepository.saveAll(mapToProduct(productData));
        } catch (IOException e) {
            throw new IllegalArgumentException(ErrorCode.FILE_IO_ERROR.getMessage());
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(ErrorCode.FILE_NOT_FOUND_ERROR.getMessage());
        }
    }

    private List<Product> mapToProduct(List<String> products) {
        return products.stream()
                .map(ConvenienceStoreInitParser::parseData)
                .map(this::createProduct)
                .toList();
    }

    private Product createProduct(List<String> productData) {
        Promotion promotion = findPromotionByName(productData
                .get(ProductFileConstant.PRODUCT_PROMOTION_INDEX));

        return new Product(
                productData.get(ProductFileConstant.PRODUCT_NAME_INDEX),
                Integer.parseInt(productData.get(ProductFileConstant.PRODUCT_PRICE)),
                Integer.parseInt(productData.get(ProductFileConstant.PRODUCT_QUANTITY_INDEX)),
                promotion
        );
    }

    private Promotion findPromotionByName(String name) {
        return promotionRepository.findByName(name)
                .orElse(null);
    }

    public void initPromotions() {
        try (BufferedReader reader = makeBufferedReader(PromotionFileConstant.PROMOTION_FILE)) {
            List<String> promotionData = reader.lines().skip(1).toList();
            promotionRepository.saveAll(mapToPromotion(promotionData));
        } catch (IOException e) {
            throw new IllegalArgumentException(ErrorCode.FILE_IO_ERROR.getMessage());
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(ErrorCode.FILE_NOT_FOUND_ERROR.getMessage());
        }
    }

    private List<Promotion> mapToPromotion(List<String> promotionData) {
        return promotionData.stream()
                .map(ConvenienceStoreInitParser::parseData)
                .map(this::createPromotion)
                .toList();
    }

    private Promotion createPromotion(List<String> promotionData) {
        return new Promotion(
                promotionData.get(PROMOTION_NAME_INDEX),
                Integer.parseInt(promotionData.get(PROMOTION_BUY_INDEX)),
                Integer.parseInt(promotionData.get(PROMOTION_GET_INDEX)),
                parseToLocalDate(promotionData.get(PROMOTION_START_DATE_INDEX)),
                parseToLocalDate(promotionData.get(PROMOTION_END_DATE_INDEX))
        );
    }

    private LocalDate parseToLocalDate(String localDateTime) {
        return LocalDate.parse(localDateTime);
    }

    private BufferedReader makeBufferedReader(String filePath) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(filePath);
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }
}
