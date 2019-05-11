package com.sjsu.project.cmpe202.repository;

import com.sjsu.project.cmpe202.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findAll();
    Item findItemByProduct(String product);
    Item findItemById(int id);
}
