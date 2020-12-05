package jpabook.jpashop.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders") // 테이블명 지정
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성메소드가 있기때문에 유지보수와 통일성을 위해 다른 class에서 생성자 호출을 막아둔다
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계
    @JoinColumn(name = "member_id")  // 매핑을 무엇으로 할것인가. FK 지정!!
    private Member member;

    // Order가 persist 될때 cascade = CascadeType.ALL가 붙은 OrderItem도 강제로 persist됨
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) 
    private List<OrderItem> orderItems = new ArrayList<>();

    // Order가 persist 될때 cascade = CascadeType.ALL가 붙은 delivery도 강제로 persist됨
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) 
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문시간

    @Enumerated(EnumType.STRING) // ORDINAL은 1,2,3 ... n 숫자로 설정됨 중간에 status 상태가 추가 되면 망한다고함 고로 STRING 사용권장!!
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]


    // -- 연관관계 메서드 -- // 
    public void setMember(Member member) {

        this.member = member;
        member.getOrders().add(this);

    }

    public void addOrderItem(OrderItem orderItem) {

        orderItems.add(orderItem);
        orderItem.setOrder(this);

    }

    public void setDelivery(Delivery delivery) {

        this.delivery = delivery;
        delivery.setOrder(this);

    }

    // 비즈니스 로직 추가 !! 

    // 생성 메소드
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {

        Order order = new Order();

        order.setMember(member);
        order.setDelivery(delivery);

        for(OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    // 주문 취소 비즈니스 로직!!
    public void cancel() {

        if(delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송 완료된 상태입니다 :D");
        }

        this.setStatus(OrderStatus.CANCEL);

        for(OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    // 전체 주문 가격 조회 로직 !!
    public int getTotalPrice() {

        int totalPrice = 0;

        for(OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }

        return totalPrice;

        // return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum(); 위의 로직과 같은 기능을 함 람다

    }

}
