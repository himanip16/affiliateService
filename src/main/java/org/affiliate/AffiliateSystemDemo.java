package org.affiliate;

import org.affiliate.model.Order;
import org.affiliate.service.CommissionCalculator;
import org.affiliate.service.OrderProcessor;
import org.affiliate.service.impl.DefaultOrderProcessor;
import org.affiliate.service.impl.PercentageCommissionCalculator;
import org.affiliate.storage.InMemoryStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

// Main class to demonstrate usage
public class AffiliateSystemDemo {

    public static void main(String[] args) throws Exception {
        // Initialize system
        InMemoryStorage storage = new InMemoryStorage();
        CommissionCalculator calculator = new PercentageCommissionCalculator(new BigDecimal("0.10")); // 10%
        OrderProcessor orderProcessor = new DefaultOrderProcessor(calculator, storage);


        // Process a new order
        Order order = new Order("customer123", "influencer456", new BigDecimal("100.00"));
        order = orderProcessor.processOrder(order);
        System.out.printf("New order created: %s%n", order);

        // Complete the order
        orderProcessor.completeOrder(order.getId());
        orderProcessor.deliverOrder(order.getId());
        System.out.printf("Order completed: %s%n", storage.findOrderById(order.getId()).get());

        // Process a return
        Order returnOrder = new Order("customer789", "influencer456", new BigDecimal("150.00"));
        returnOrder = orderProcessor.processOrder(returnOrder);
        orderProcessor.returnOrder(returnOrder.getId());
        System.out.printf("Order returned: %s%n", storage.findOrderById(returnOrder.getId()).get());
        System.out.printf("Commission status: %s%n", storage.findCommissionByOrderId(returnOrder.getId()).get());
    }
}
