package com.sjsu.project.cmpe202.repository;

import com.sjsu.project.cmpe202.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Integer> {

    @Query("FROM Card c WHERE c.user.id = :userId")
    List<Card> findCardsByUser(@Param("userId") Integer userId);

    Card findCardById(Integer id);

    @Query("FROM Card c WHERE c.cardNumber = :cardNumber")
    Card findCardByCardNumber(@Param("cardNumber") String cardNumber);

}
