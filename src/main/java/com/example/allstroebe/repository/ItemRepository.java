package com.example.allstroebe.repository;

import com.example.allstroebe.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
