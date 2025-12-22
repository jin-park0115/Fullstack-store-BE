package com.example.allstroebe.service;

import com.example.allstroebe.dto.CartDetailDto;
import com.example.allstroebe.dto.CartItemDto;
import com.example.allstroebe.entity.Cart;
import com.example.allstroebe.entity.CartItem;
import com.example.allstroebe.entity.Item;
import com.example.allstroebe.entity.Member;
import com.example.allstroebe.repository.CartItemRepository;
import com.example.allstroebe.repository.CartRepository;
import com.example.allstroebe.repository.ItemRepository;
import com.example.allstroebe.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor

public class CartService {
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final CartItemRepository cartItemRepository;

    public Long addCart(CartItemDto cartItemDto, String email){
        // 1.로그인한 회원 정보 찾기
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원정보가 없습니다."));
        // 2.이 회원의 장바구니가 있는지 확인
        Cart cart = cartRepository.findByMemberId(member.getId());

        // 3. 장바구니가 없으면 새로 하나 만들어준다.
        if (cart == null) {
            cart = new Cart();
            cart.setMember(member);
            cartRepository.save(cart);
        }

        // 4. 담으려는 상품 확인
        Item item = itemRepository.findById(cartItemDto.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("상품 정보가 없습니다."));

        // 5. 장바구니에 이미 이 상품이 있는지 확인
        CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());

        if (savedCartItem != null) {
            // 이미 있으면 수량만 증가 (기존 수량 + 새 수량)
            savedCartItem.setCount(savedCartItem.getCount() + cartItemDto.getCount());
            return savedCartItem.getId();
        } else {
            // 없으면 새로 담기
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setItem(item);
            cartItem.setCount(cartItemDto.getCount());

            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }
    }
    @Transactional(readOnly = true)
    public List<CartDetailDto> getCartList(String email){
        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();

        //1. 회원정보 조회
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("회원정보가 없습니다."));
        //2. 장바구니 조회
        Cart cart = cartRepository.findByMemberId(member.getId());
        if(cart == null){
            return cartDetailDtoList; //장바구니가 비어있으면 빈 리스트 반환
        }
        //3. 장바구니 아이템들 조회
        List<CartItem> cartItemList = cartItemRepository.findByCartIdOrderByIdDesc(cart.getId());
        //4. CartItem 엔티티를 CartDetailDto로 변환 (화면에 필요한 정보만 추출)
        for (CartItem cartItem : cartItemList) {
            CartDetailDto cartDetailDto = new CartDetailDto(
                    cartItem.getId(),
                    cartItem.getItem().getItemName(),
                    cartItem.getItem().getPrice(),
                    cartItem.getCount()
            );
            cartDetailDtoList.add(cartDetailDto);
        }

        return cartDetailDtoList;
    }
}
