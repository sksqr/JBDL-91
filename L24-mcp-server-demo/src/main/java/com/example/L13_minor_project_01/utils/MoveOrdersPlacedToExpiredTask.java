package com.example.L13_minor_project_01.utils;

import com.example.L13_minor_project_01.entity.Order;
import com.example.L13_minor_project_01.entity.OrderStatus;
import com.example.L13_minor_project_01.repo.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class MoveOrdersPlacedToExpiredTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoveOrdersPlacedToExpiredTask.class);
    private final OrderRepository orderRepository;

    public MoveOrdersPlacedToExpiredTask(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

//    @Scheduled(fixedDelay = 3000)
    @Transactional
    public void markOrdersExpire() {
        LOGGER.info("Starting markOrdersExpire");

        LocalDateTime threshold = LocalDateTime.now().minusHours(3);
        List<Order> expiredCandidates = orderRepository.findByStatusAndUpdatedAtBefore(OrderStatus.PLACED, threshold);

        for (Order order : expiredCandidates) {
            order.setStatus(OrderStatus.EXPIRED);
        }

        if (!expiredCandidates.isEmpty()) {
            orderRepository.saveAll(expiredCandidates);
        }

        LOGGER.info("Expired {} placed orders older than {}", expiredCandidates.size(), threshold);
        LOGGER.info("Exiting markOrdersExpire");
    }
}
