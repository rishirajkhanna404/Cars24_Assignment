package com.rishi.aicopilot.seed;

import com.rishi.aicopilot.model.*;
import com.rishi.aicopilot.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final OrderRepository orderRepository;

    @Override
    public void run(String... args) {
        log.info("Seeding order data...");

        List<Order> orders = new ArrayList<>();

        // Specifically requested demo orders
        orders.add(Order.builder()
                .id(4521L)
                .customerName("Alice Smith")
                .status(OrderStatus.PROCESSING)
                .paymentStatus(PaymentStatus.PAID)
                .orderDate(LocalDate.now().minusDays(5))
                .deliveryDate(null)
                .amount(new BigDecimal("150.00"))
                .build());

        orders.add(Order.builder()
                .id(1289L)
                .customerName("Bob Johnson")
                .status(OrderStatus.PLACED)
                .paymentStatus(PaymentStatus.PAID)
                .orderDate(LocalDate.now().minusDays(2))
                .deliveryDate(null) // "paid but delivery isn't scheduled"
                .amount(new BigDecimal("299.99"))
                .build());

        orders.add(Order.builder()
                .id(2231L)
                .customerName("Charlie Brown")
                .status(OrderStatus.DELIVERED)
                .paymentStatus(PaymentStatus.PAID)
                .orderDate(LocalDate.now().minusDays(10))
                .deliveryDate(LocalDate.now().minusDays(3))
                .amount(new BigDecimal("45.50"))
                .build());

        // Generate ~27 more realistic orders
        Random random = new Random(42); // Deterministic seed
        for (int i = 1; i <= 27; i++) {
            long id = 1000L + i;
            if (id == 4521L || id == 1289L || id == 2231L) continue; // Avoid duplicates

            OrderStatus status = OrderStatus.values()[random.nextInt(OrderStatus.values().length)];
            PaymentStatus payment = PaymentStatus.values()[random.nextInt(PaymentStatus.values().length)];

            LocalDate orderDate = LocalDate.now().minusDays(random.nextInt(30));
            LocalDate deliveryDate = null;
            if (status == OrderStatus.DELIVERED) {
                deliveryDate = orderDate.plusDays(random.nextInt(7) + 1);
            }

            orders.add(Order.builder()
                    .id(id)
                    .customerName("Customer " + i)
                    .status(status)
                    .paymentStatus(payment)
                    .orderDate(orderDate)
                    .deliveryDate(deliveryDate)
                    .amount(BigDecimal.valueOf(10 + (990 * random.nextDouble())))
                    .build());
        }

        orderRepository.saveAll(orders);
        log.info("Successfully seeded {} orders.", orders.size());
    }
}
