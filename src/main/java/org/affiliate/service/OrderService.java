package org.affiliate.service;

import org.affiliate.constants.OrderStatus;
import org.affiliate.model.Order;

import java.util.List;

public class OrderService {

    public void processOrder(Order order) {
        // Process the order
    }

    public void completeOrder(String orderId) {
        // Complete the order
    }

    public void returnOrder(String orderId) {
        // Return the order
    }

    public void deliverOrder(String orderId) {
        // Deliver the order
    }

    public Order findOrderById(String orderId) {
        // Find the order by id
        return null;
    }

    public void saveOrder(Order order) {
        // Save the order
    }

    public List<Order> findOrderByStatus(OrderStatus status) {
        // Find the order by status
        return null;
    }
}
