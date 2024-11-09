package store.exception;

public enum ErrorCode {
    FILE_NOT_FOUND_ERROR("파일이 존재하지 않습니다"),
    FILE_IO_ERROR("파일을 불러올 수 없습니다"),
    ORDER_PRODUCT_FORMAT_ERROR("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    ;

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
