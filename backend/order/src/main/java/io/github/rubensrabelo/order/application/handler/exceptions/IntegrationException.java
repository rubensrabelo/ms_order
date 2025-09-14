package io.github.rubensrabelo.order.application.handler.exceptions;

import feign.FeignException;

public class IntegrationException extends  RuntimeException {
    public IntegrationException(String msg) {
        super(msg);
    }

    public IntegrationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
