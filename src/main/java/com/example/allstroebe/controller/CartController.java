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
    public String addCart(@RequestBody CartItemDto cartItemDto, Principal principal){
        // Principal: 스프링 시큐리티가 토큰을 해석해서 넣어둔 '로그인한 사람 정보'
        if(principal == null){
            return "로그인이 필요합니다";
        }

        String email = principal.getName(); //토큰에 저장된 이메일 꺼내기

        try {
            cartService.addCart(cartItemDto, email);
            return "장바구니 담기 성공!";
        } catch (Exception e){
            return "실패: " + e.getMessage();
        }
    }

    @GetMapping("list")
    public ResponseEntity getCartList(Principal principal){
        if (principal == null){
            return new ResponseEntity("로그인이 필요합니다", HttpStatus.UNAUTHORIZED);
        }
        // 서비스에서 현재 로그인한 사용자의 장바구니 아이템을 가져오는 로직
        List<CartDetailDto> cartDetailList = cartService.getCartList(principal.getName());
        return new ResponseEntity<>(cartDetailList, HttpStatus.OK);
    }
}
