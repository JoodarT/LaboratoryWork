package org.edufood.edufood.repository;

import org.edufood.edufood.entities.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /** Заказ по id только если он принадлежит указанному пользователю — защита от IDOR. */
    @EntityGraph(attributePaths = {"restaurant", "items", "items.dish"})
    Optional<Order> findByIdAndUserEmail(Long id, String email);

    /** История заказов пользователя, новые сверху (для задачи #9). */
    @EntityGraph(attributePaths = {"restaurant", "items", "items.dish"})
    List<Order> findByUserEmailOrderByCreatedAtDesc(String email);
}
