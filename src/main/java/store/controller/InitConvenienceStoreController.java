package store.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import store.constant.InitConvenienceStoreConstant;
import store.exception.ErrorCode;

public class InitConvenienceStoreController {
    public void initProducts() {
        try (BufferedReader reader = makeBufferedReader(InitConvenienceStoreConstant.PRODUCT_FILE)) {
            List<String> products = reader.lines().skip(1)
                    .toList();
        } catch (IOException e) {
            throw new IllegalArgumentException(ErrorCode.FILE_IO_ERROR.getMessage());
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(ErrorCode.FILE_NOT_FOUND_ERROR.getMessage());
        }
    }

    private BufferedReader makeBufferedReader(String filePath) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(filePath);
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    public void initPromotions() {
    }
}
