package jpabook.jpashop.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("B")
@Getter @Setter
public class Book extends Item {

   private String author;

   private String isbn;

   public void change(String name, int price, int stockQuantity, String author, String isbn) {

      this.setName(name);
      this.setPrice(price);
      this.setStockQuantity(stockQuantity);
      this.setAuthor(author);
      this.setIsbn(isbn);

   }
    
}
