package store.controller;

import static store.constant.InitConvenienceStoreConstant.PROMOTION_BUY_INDEX;
import static store.constant.InitConvenienceStoreConstant.PROMOTION_END_DATE_INDEX;
import static store.constant.InitConvenienceStoreConstant.PROMOTION_GET_INDEX;
import static store.constant.InitConvenienceStoreConstant.PROMOTION_NAME_INDEX;
import static store.constant.InitConvenienceStoreConstant.PROMOTION_START_DATE_INDEX;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import store.constant.InitConvenienceStoreConstant;
import store.exception.ErrorCode;
import store.model.Promotion;
import store.model.repository.PromotionRepository;
import store.util.ParseConvenienceStoreInitUtil;

public class InitConvenienceStoreController {
    private final PromotionRepository promotionRepository = new PromotionRepository();

    public void initPromotions() {
        try (BufferedReader reader = makeBufferedReader(InitConvenienceStoreConstant.PROMOTION_FILE)) {
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
                .map(ParseConvenienceStoreInitUtil::parseData)
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
