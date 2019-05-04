package com.sjsu.project.cmpe202.repository;

import com.sjsu.project.cmpe202.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Query("FROM Payment p WHERE p.user.id = :userId")
    List<Payment> findPaymentsByUser(@Param("userId") Integer userId);

    @Query("FROM Payment p WHERE p.card.id = :cardId")
    List<Payment> findPaymentsByCard(@Param("cardId") Integer cardId);

    Payment findPaymentById(Integer id);
}
