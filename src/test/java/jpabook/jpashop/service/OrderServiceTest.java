package jpabook.jpashop.service;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    public void 상품주문취소() throws Exception {

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

        em.flush();

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        orderService.cancelOrder(orderId);

        Order getOrder = orderRepository.findOne(orderId);

        // 주문 취소시 주문 상태 CANCEL 여부 확인 TEST
        assertEquals(OrderStatus.CANCEL, getOrder.getStatus());
        // 주문이 취소된 상품의 재고수 다시 증가 처리 여부 확인 TEST
        assertEquals(10, book.getStockQuantity());



    }

    // @Test
    // public void 상품주문_재고수량초과() throws Exception {

    //     Member member = new Member();
    //     member.setName("회원1");
    //     member.setAddress(new Address("경기", "하남", "123-123"));
    //     em.persist(member);

    //     Item book = new Book();
    //     book.setName("시골 JPA");
    //     book.setPrice(10000);
    //     book.setStockQuantity(10);
    //     em.persist(book);

    //     // 재고는 10개인데 주문개수는 11개 
    //     // NotEnoughStockExcetion 발생해야함
    //     int orderCount = 11;
        
    //     em.flush();

    //     orderService.order(member.getId(), book.getId(), orderCount);

    //     // 정상적으로 재고가 부족하다면 NotEnoughStockExcetion 발생
    //     // 재고가 부족하지 않으면 fail로 넘어가야 정상입니다!!

    //     fail("재고 수량 부족 예외가 발생해야 한다.");


    // }

    @Test
    public void 상품주문_재고수량초과() throws Exception {

        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("경기", "하남", "123-123"));
        em.persist(member);

        Item book = new Book();
        book.setName("시골 JPA");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);

        // 재고는 10개인데 주문개수는 11개 
        // NotEnoughStockExcetion 발생해야함
        int orderCount = 11;

        em.flush();

        orderService.order(member.getId(), book.getId(), orderCount);

        // 정상적으로 재고가 부족하다면 NotEnoughStockExcetion 발생
        // 재고가 부족하지 않으면 fail로 넘어가야 정상입니다!!

        fail("재고 수량 부족 예외가 발생해야 한다.");


    }


}
