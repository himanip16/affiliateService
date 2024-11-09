package org.affiliate.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.affiliate.constants.OrderStatus;

@Setter
@Getter
@ToString
public class Order {
    private final String id;
    private final String customerId;
    private final String influencerId;
    private final BigDecimal amount;
    private final LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private OrderStatus status;
    private LocalDateTime returnableBy;

    public Order(String customerId, String influencerId, BigDecimal amount) {
        this.id = UUID.randomUUID().toString();
        this.customerId = customerId;
        this.influencerId = influencerId;
        this.amount = amount;
        this.orderDate = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
    }

    @Override
    public String toString() {
        return String.format("Order[id=%s%n, customerId=%s%n, influencerId=%s%n, amount=%s%n, status=%s%n]",
                id, customerId, influencerId, amount, status);
    }
}
