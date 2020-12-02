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

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders") // 테이블명 지정
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계
    @JoinColumn(name = "member_id")  // 매핑을 무엇으로 할것인가. FK 지정!!
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // 일대일 관계
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

}
