package ru.momentum.finstrument.api.bitrix.exceptions;

public class BitrixApiException extends Exception {
    public BitrixApiException(Throwable cause) {
        super(cause);
    }

    public BitrixApiException(String message) {
        super(message);
    }
}
