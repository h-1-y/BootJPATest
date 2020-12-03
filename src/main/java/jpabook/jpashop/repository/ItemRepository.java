package jpabook.jpashop.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    
    private final EntityManager em;

    // 상품 등록
    public void save(Item item) {

        if(item.getId() == null) {
            em.persist(item); // 
        } else {
            em.merge(item); // Update와 비슷하다고
        }

    }
    // 상품 한건 조회
    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }
    // 여러 상품 조회
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }

}
