package com.example.allstroebe.controller;

import com.example.allstroebe.dto.CartDetailDto;
import com.example.allstroebe.dto.CartItemDto;
import com.example.allstroebe.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity addCart(@RequestBody CartItemDto cartItemDto, Principal principal){
        // 1. Principal이 null이면 명확하게 401 Unauthorized 에러 반환
        if(principal == null){
            return new ResponseEntity<>("로그인이 필요합니다", HttpStatus.UNAUTHORIZED);
        }

        String email = principal.getName();

        try {
            Long cartItemId = cartService.addCart(cartItemDto, email);
            return new ResponseEntity<>("장바구니 담기 성공! ID: " + cartItemId, HttpStatus.OK);
        } catch (Exception e){
            // 2. 실패 시 400 Bad Request와 에러 메시지 반환
            return new ResponseEntity<>("실패: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/list") // 경로 앞에 /를 붙여주는 것이 정석입니다.
    public ResponseEntity getCartList(Principal principal){
        if (principal == null){
            return new ResponseEntity<>("로그인이 필요합니다", HttpStatus.UNAUTHORIZED);
        }

        List<CartDetailDto> cartDetailList = cartService.getCartList(principal.getName());
        return new ResponseEntity<>(cartDetailList, HttpStatus.OK);
    }
}