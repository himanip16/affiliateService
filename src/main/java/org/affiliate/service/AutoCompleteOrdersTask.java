package org.affiliate.service;

import org.affiliate.constants.OrderStatus;
import org.affiliate.model.Order;
import org.affiliate.storage.InMemoryStorage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AutoCompleteOrdersTask {
    private final OrderProcessor orderProcessor;
    private final OrderService orderService;

    public AutoCompleteOrdersTask(OrderProcessor orderProcessor, OrderService orderService) {
        this.orderProcessor = orderProcessor;
        this.orderService = orderService;
    }

    public void start() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::autoCompleteOrders, 0, 1, TimeUnit.HOURS);
    }

    private void autoCompleteOrders() {
        System.out.println("Running auto-complete orders task...");
        List<Order> orders = orderService.findOrderByStatus(OrderStatus.DELIVERED);

        for (Order order : orders) {
            if (order.getReturnableBy().isBefore(LocalDateTime.now())) {
                try {
                    orderProcessor.completeOrder(order.getId());
                    System.out.printf("Auto-completed order: %s%n", order.getId());
                } catch (Exception e) {
                    System.err.printf("Failed to auto-complete order: %s, Error: %s%n", order.getId(), e.getMessage());
                }
            }
        }
    }
}
