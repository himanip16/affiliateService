package org.affiliate.service;

import org.affiliate.model.Order;

import java.math.BigDecimal;

public interface CommissionCalculator {
    BigDecimal calculateCommission(Order order);
}
