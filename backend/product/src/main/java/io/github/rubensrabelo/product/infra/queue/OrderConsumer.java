package io.github.rubensrabelo.product.infra.queue;

import io.github.rubensrabelo.product.application.dto.order.OrderCreatedEvent;
import io.github.rubensrabelo.product.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE)
    public void receiveOrder(OrderCreatedEvent order) {
        System.out.println("Order received: " + order.id());
    }
}