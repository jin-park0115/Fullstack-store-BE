package com.example.allstroebe.controller;

import com.example.allstroebe.entity.Item;
import com.example.allstroebe.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public List<Item> getItemList() {
        return itemRepository.findAll();
    }
}
