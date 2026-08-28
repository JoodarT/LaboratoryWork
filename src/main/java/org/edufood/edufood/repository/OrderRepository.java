package org.edufood.edufood.repository;

import org.edufood.edufood.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT DISTINCT o FROM Order o " +
            "LEFT JOIN FETCH o.items i " +
            "LEFT JOIN FETCH i.dish " +
            "WHERE LOWER(o.user.email) = LOWER(:email) " +
            "ORDER BY o.createdAt DESC")
    List<Order> findAllByUserEmailWithItems(@Param("email") String email);
}