package com.example.allstroebe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartDetailDto {
    private Long cartItemDto; //장바구니 아이템 식별자(삭제/수정용)
    private String itemName; //상품명
    private int price; //가격
    private int count; //수량

    public CartDetailDto(Long cartItemDto, String itemName, int price, int count){
        this.cartItemDto = cartItemDto;
        this.itemName = itemName;
        this.price = price;
        this.count = count;
    }
}

