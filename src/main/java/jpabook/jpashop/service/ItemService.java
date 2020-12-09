package jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.item.Book;
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

    // 변경감지 dirty checking 
    // Transaction이 커밋되면 JPA에서 플러쉬를 해주고 영속성 컨텍스트의 바뀐 속성을 찾아 Update Query 해준다 변경감지방식 채택 권장!!
    // Service단 내에서 가능하면 Setter 쓰지말고 Entity안에서 바로 추적할수 있는 메소드를 만들어라!! ex) Book.java Entity class에 만들어둔 예시 메소드 참고!
    @Transactional
    //public void updateItem(Long itemId, Book form) {
    public void updateItem(Long itemId, String name, int price, int stockQuantity, String author, String isbn) {

        Book book = (Book) itemRepository.findOne(itemId);
        
        book.change(name, price, stockQuantity, author, isbn);

    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
    
    public List<Item> findItems() {
        return itemRepository.findAll();
    }
}
