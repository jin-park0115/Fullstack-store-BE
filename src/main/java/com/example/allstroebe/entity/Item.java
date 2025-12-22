package com.example.allstroebe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String itemName; //상품명

    private int price; //가격

    private int stockNumber; // 재고 수량

    private String itemDetail; //상품 상세 설명
}
