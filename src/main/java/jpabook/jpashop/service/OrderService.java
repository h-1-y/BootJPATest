package jpabook.jpashop.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    // 주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);
        
        // ------ Delivery와 orderItem의 Repository를 따로 만들어서 save해주지 않는 이유는 Order Entity 생성시 Delivery와 OrderItem의 -------
        // ------ Entity에 cascade = CascadeType.ALL 옵션을 추가해줬기때문에 Delivery와 orderItem는 자동으로 persist 된다고 함 --------------
        // 배송정보 생성
        Delivery delivery = new Delivery();
        // 회원의 주문정보로 배송정보를 생성
        delivery.setAddress(member.getAddress()); 

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        // ------------------------------------------------------------------------------------------------------------------------------

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order); // ----------------------- 고로 얘만 save() <- persist 해주면 됨 !!

        return order.getId();

    }

    // 주문 취소
    @Transactional
    public void cancelOrder(Long orderId) {

        // 주문 Entity 조회
        Order order = orderRepository.findOne(orderId);
        // 주문 취소 처리
        order.cancel();

    }


}