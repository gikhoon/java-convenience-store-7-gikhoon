package store.util;

import store.exception.ErrorCode;

public class YesOrNoParser {
    private static final String YES = "Y";
    private static final String NO = "N";

    public static boolean parseYesOrNo(String yesOrNo) {
        if (yesOrNo.equals(YES)) {
            return true;
        }
        if (yesOrNo.equals(NO)) {
            return false;
        }
        throw new IllegalArgumentException(ErrorCode.YES_OR_NO_ERROR.getMessage());
    }
}
