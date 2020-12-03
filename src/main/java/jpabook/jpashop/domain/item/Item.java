package jpabook.jpashop.domain.item;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockExcetion;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item { // 추상클래스

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;
    
    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items") // 다대다 관계
    private List<Category> categories = new ArrayList<>();

    // --비즈니스 로직-- Data(stockQuantity)를 가지고 있는쪽에 비즈니스 로직을 추가하는것이 가장 좋다. 응집력이 있다//

    // 재고 증가 로직
    public void addStock(int quantity) {

        this.stockQuantity += quantity;

    }
    // 재고 감소 로직
    public void removeStock(int quantity) {

        int restStock = this.stockQuantity - quantity;

        if(restStock < 0) {
            throw new NotEnoughStockExcetion("need more stock");
        }

        this.removeStock(quantity);

    }

}
