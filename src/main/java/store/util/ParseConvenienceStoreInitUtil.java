package store.util;

import java.util.Arrays;
import java.util.List;

public class ParseConvenienceStoreInitUtil {
    private static final String SEPARATOR = ",";

    public static List<String> parseData(String data) {
        return Arrays.stream(data.split(SEPARATOR)).toList();
    }
}
