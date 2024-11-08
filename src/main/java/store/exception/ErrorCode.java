package store.exception;

public enum ErrorCode {
    FILE_NOT_FOUND_ERROR("파일이 존재하지 않습니다"),
    FILE_IO_ERROR("파일을 불러올 수 없습니다"),
    ;

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
