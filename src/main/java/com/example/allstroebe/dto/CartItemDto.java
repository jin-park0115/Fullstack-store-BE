// 프론트에서 '사과 2개담을래' 라고 보낼 때 사용할 객체
package com.example.allstroebe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartItemDto {
    private Long itemId; //상품 아이디
    private int count; // 수량(몇 개 담을지)
}
