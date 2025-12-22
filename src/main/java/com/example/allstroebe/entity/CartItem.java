package com.example.allstroebe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cart cart; // 어떤 장바구니인지

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item; // 실제 상품 엔터티

    private int count; //담은 수량

    public void addCount(int count){
        this.count += count;
    }
}
