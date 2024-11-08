package store.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import store.constant.InitConvenienceStoreConstant;
import store.exception.ErrorCode;
import store.model.Promotion;
import store.util.ParseConvenienceStoreInitUtil;

public class InitConvenienceStoreController {
    public void initPromotions() {
        try (BufferedReader reader = makeBufferedReader(InitConvenienceStoreConstant.PROMOTION_FILE)) {
            List<String> promotionData = reader.lines()
                    .skip(1)
                    .toList();
            List<Promotion> promotions = mapToPromotion(promotionData);
        } catch (IOException e) {
            throw new IllegalArgumentException(ErrorCode.FILE_IO_ERROR.getMessage());
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(ErrorCode.FILE_NOT_FOUND_ERROR.getMessage());
        }
    }

    private List<Promotion> mapToPromotion(List<String> promotionData) {
        return promotionData.stream()
                .map(ParseConvenienceStoreInitUtil::parseData)
                .map(this::createPromotion)
                .toList();
    }

    private Promotion createPromotion(List<String> promotionData) {
        return null;
    }

    private BufferedReader makeBufferedReader(String filePath) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(filePath);
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }
}
