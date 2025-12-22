package com.example.allstroebe.repository;

import com.example.allstroebe.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    //특정 장바구니에 특정 상품이 담겨있는지 확인하는 메서드
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);
    // 장바구니 ID로 장바구니에 담긴 모든 상품 리스트 조회
    List<CartItem> findByCartIdOrderByIdDesc(Long cartId);
}

