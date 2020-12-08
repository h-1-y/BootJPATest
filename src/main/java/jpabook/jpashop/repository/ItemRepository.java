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
            // 병합 !! 병합은 사용하는 모든 속성이 변경된다. 병합시 값이 없으면 Null로 업데이트를 할 위험이 있다.
            // merge는 속성을 선택하여 update하기에 제한적 변경감지를 많이 쓴다고한다...
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
