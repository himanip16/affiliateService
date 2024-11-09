package org.affiliate.storage;

import org.affiliate.model.Commission;
import org.affiliate.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryStorage {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryStorage.class);

    private final Map<String, Order> orders = new ConcurrentHashMap<>();
    private final Map<String, Commission> commissions = new ConcurrentHashMap<>();

    // Order operations
    public Order saveOrder(Order order) {
        System.out.printf("Saving order: %s%n", order);
        orders.put(order.getId(), order);
        return order;
    }

    public Optional<Order> findOrderById(String id) {
        System.out.printf("Finding order by id: %s%n", id);
        return Optional.ofNullable(orders.get(id));
    }

    // Commission operations
    public Commission saveCommission(Commission commission) {
        System.out.printf("Saving commission: %s%n", commission);
        commissions.put(commission.getId(), commission);
        return commission;
    }

    public Optional<Commission> findCommissionById(String id) {
        System.out.printf("Finding commission by id: %s%n", id);
        return Optional.ofNullable(commissions.get(id));
    }

    public Optional<Commission> findCommissionByOrderId(String orderId) {
        System.out.printf("Finding commission by order id: %s%n", orderId);
        return commissions.values().stream()
                .filter(c -> c.getOrderId().equals(orderId))
                .findFirst();
    }
}
