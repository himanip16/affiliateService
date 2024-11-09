package org.affiliate.service.impl;

import org.affiliate.model.Order;
import org.affiliate.service.CommissionCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class PercentageCommissionCalculator implements CommissionCalculator {
    private static final Logger logger = LoggerFactory.getLogger(PercentageCommissionCalculator.class);
    private final BigDecimal commissionPercentage;

    public PercentageCommissionCalculator(BigDecimal commissionPercentage) {
        this.commissionPercentage = commissionPercentage;
    }

    @Override
    public BigDecimal calculateCommission(Order order) {
        System.out.printf("Calculating commission for order: %s%n", order.getId());
        BigDecimal commission = order.getAmount().multiply(commissionPercentage);
        System.out.printf("Commission calculated: %s%n for order amount: %s%n", commission, order.getAmount());
        return commission;
    }
}
