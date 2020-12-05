package jpabook.jpashop.service;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("경기", "하남", "123-123"));
        em.persist(member);

        Item book = new Book();
        book.setName("시골 JPA");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        Order getOrder = orderRepository.findOne(orderId);

        em.flush();

        // 상품 주문시 상태 ORDER TEST
        assertEquals(OrderStatus.ORDER, getOrder.getStatus());
        // 주문한 상품 종류 수 TEST
        assertEquals(1, getOrder.getOrderItems().size());
        // 주문 가격 TEST
        assertEquals(10000 * orderCount, getOrder.getTotalPrice());
        // 주문 수량만큼 재고 상태 TEST
        assertEquals(8, book.getStockQuantity());
        

    }

    
    @Test
    public void 상품취소() throws Exception {



    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {



    }


}
