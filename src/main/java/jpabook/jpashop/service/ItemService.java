package jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    
    private final ItemRepository itemRepository;

    @Transactional // class에 Transactional 어노테이션이 readOnly = true 설정되있기때문에 insert기능을 할 save로직에는 따로 @Transactional 추가
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
    
    public List<Item> findItems() {
        return itemRepository.findAll();
    }
}
