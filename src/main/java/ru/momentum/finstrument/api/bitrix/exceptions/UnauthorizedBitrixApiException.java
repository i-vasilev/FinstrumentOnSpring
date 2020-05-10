package ru.momentum.finstrument.api.bitrix.exceptions;

public class UnauthorizedBitrixApiException extends BitrixApiHttpException {
    public UnauthorizedBitrixApiException(String message, int status) {
        super(message, status);
    }
}
