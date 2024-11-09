import org.affiliate.*;
import org.affiliate.constants.CommissionStatus;
import org.affiliate.constants.OrderStatus;
import org.affiliate.model.Commission;
import org.affiliate.model.Order;
import org.affiliate.service.CommissionCalculator;
import org.affiliate.service.OrderProcessor;
import org.affiliate.service.impl.DefaultOrderProcessor;
import org.affiliate.service.impl.PercentageCommissionCalculator;
import org.affiliate.storage.InMemoryStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AffiliateSystemTest {
    private InMemoryStorage storage;
    private CommissionCalculator calculator;
    private OrderProcessor orderProcessor;

    @BeforeEach
    void setUp() {
        storage = new InMemoryStorage();
        calculator = new PercentageCommissionCalculator(new BigDecimal("0.10"));
        orderProcessor = new DefaultOrderProcessor(calculator, storage);
    }

    @Test
    void processOrder_ShouldCreateOrderAndCommission() {
        // Given
        Order order = new Order("customer1", "influencer1", new BigDecimal("100.00"));

        // When
        Order processedOrder = orderProcessor.processOrder(order);

        // Then
        assertTrue(storage.findOrderById(processedOrder.getId()).isPresent());
        assertTrue(storage.findCommissionByOrderId(processedOrder.getId()).isPresent());
        assertEquals(new BigDecimal("10.0000"),
                storage.findCommissionByOrderId(processedOrder.getId()).get().getAmount());
    }

    @Test
    void returnOrder_ShouldUpdateOrderAndCancelCommission() throws Exception {
        // Given
        Order order = new Order("customer1", "influencer1", new BigDecimal("100.00"));
        Order processedOrder = orderProcessor.processOrder(order);

        // When
        orderProcessor.returnOrder(processedOrder.getId());

        // Then
        Order returnedOrder = storage.findOrderById(processedOrder.getId()).get();
        Commission commission = storage.findCommissionByOrderId(processedOrder.getId()).get();

        assertEquals(OrderStatus.RETURNED, returnedOrder.getStatus());
        assertEquals(CommissionStatus.CANCELLED, commission.getStatus());
    }

    @Test
    void completeOrder_ShouldUpdateOrderStatus() throws Exception {
        // Given
        Order order = new Order("customer1", "influencer1", new BigDecimal("100.00"));
        Order processedOrder = orderProcessor.processOrder(order);

        // When
        orderProcessor.completeOrder(processedOrder.getId());

        // Then
        Order completedOrder = storage.findOrderById(processedOrder.getId()).get();
        assertEquals(OrderStatus.COMPLETED, completedOrder.getStatus());
    }
}
