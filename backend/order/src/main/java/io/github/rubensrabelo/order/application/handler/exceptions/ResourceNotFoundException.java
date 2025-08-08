package io.github.rubensrabelo.order.application.handler.exceptions;

public class ResourceNotFoundException extends  RuntimeException {
    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
