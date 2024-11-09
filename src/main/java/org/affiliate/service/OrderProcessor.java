package org.affiliate.service;

import org.affiliate.model.Order;

public interface OrderProcessor {
    Order processOrder(Order order);

    void completeOrder(String orderId) throws Exception;

    void returnOrder(String orderId) throws Exception;

    void deliverOrder(String orderId) throws Exception;
}
