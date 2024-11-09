package org.affiliate.service.impl;

import org.affiliate.*;
import org.affiliate.constants.CommissionStatus;
import org.affiliate.constants.OrderStatus;
import org.affiliate.model.Commission;
import org.affiliate.model.Order;
import org.affiliate.service.CommissionCalculator;
import org.affiliate.service.OrderProcessor;
import org.affiliate.storage.InMemoryStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DefaultOrderProcessor implements OrderProcessor {
    private static final Logger logger = LoggerFactory.getLogger(DefaultOrderProcessor.class);
    private final CommissionCalculator commissionCalculator;
    private final InMemoryStorage storage;

    public DefaultOrderProcessor(CommissionCalculator commissionCalculator, InMemoryStorage storage) {
        this.commissionCalculator = commissionCalculator;
        this.storage = storage;
    }

    @Override
    public Order processOrder(Order order) {
        System.out.printf("Processing new order: %s%n", order);
        storage.saveOrder(order);

        BigDecimal commissionAmount = commissionCalculator.calculateCommission(order);
        Commission commission = new Commission(order.getId(), order.getInfluencerId(), commissionAmount);
        storage.saveCommission(commission);

        System.out.printf("Order processed successfully with commission: %s%n", commission);
        return order;
    }

    @Override
    public void completeOrder(String orderId) throws Exception {
        System.out.printf("Completing order: %s%n", orderId);
        Order order = storage.findOrderById(orderId)
                .orElseThrow(() -> new Exception("Order not found: " + orderId));

        order.setStatus(OrderStatus.COMPLETED);
        storage.saveOrder(order);
        //TODO: complete commission as well
        Commission commission = storage.findCommissionByOrderId(orderId)
                .orElseThrow(() -> new Exception("Commission not found for order: " + orderId));
        commission.setStatus(CommissionStatus.PAID);
        storage.saveCommission(commission);
        System.out.printf("Commission completed: %s%n", commission);

        System.out.printf("Order completed: %s%n", order);
    }

    @Override
    public void returnOrder(String orderId) throws Exception {
        System.out.printf("Processing return for order: %s%n", orderId);

        Order order = storage.findOrderById(orderId)
                .orElseThrow(() -> new Exception("Order not found: " + orderId));
        order.setStatus(OrderStatus.RETURNED);
        storage.saveOrder(order);

        Commission commission = storage.findCommissionByOrderId(orderId)
                .orElseThrow(() -> new Exception("Commission not found for order: " + orderId));
        commission.setStatus(CommissionStatus.CANCELLED);
        storage.saveCommission(commission);

        System.out.printf("Order return processed. Order: %s%n, Commission: %s%n", order, commission);
    }

    @Override
    public void deliverOrder(String orderId) throws Exception {
        System.out.printf("Delivering order: %s%n", orderId);
        Order order = storage.findOrderById(orderId)
                .orElseThrow(() -> new Exception("Order not found: " + orderId));

        order.setStatus(OrderStatus.DELIVERED);
        order.setDeliveryDate(LocalDateTime.now());
        order.setReturnableBy(LocalDateTime.now().plusDays(10));
        storage.saveOrder(order);
        System.out.printf("Order delivered: %s%n", order);

    }

}
