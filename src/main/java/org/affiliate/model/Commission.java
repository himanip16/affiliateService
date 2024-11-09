package org.affiliate.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.affiliate.constants.CommissionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@ToString
public class Commission {
    private final String id;
    private final String orderId;
    private final String influencerId;
    private final BigDecimal amount;
    private final LocalDateTime createdAt;
    private CommissionStatus status;

    public Commission(String orderId, String influencerId, BigDecimal amount) {
        this.id = UUID.randomUUID().toString();
        this.orderId = orderId;
        this.influencerId = influencerId;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
        this.status = CommissionStatus.PENDING;
    }
}
