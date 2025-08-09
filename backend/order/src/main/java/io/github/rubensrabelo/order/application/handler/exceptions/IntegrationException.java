package io.github.rubensrabelo.order.application.handler.exceptions;

import feign.FeignException;

public class IntegrationException extends  RuntimeException {
    public IntegrationException(String msg, FeignException cause) {
        super(msg, cause);
    }
}
