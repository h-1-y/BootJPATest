package jpabook.jpashop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Member {
    
    @Id
    @GeneratedValue
    @Column( name = "member_id") // 기본키 이름 지정
    private Long id;

    private String name;

    @Embedded // 내장타입객체 어노테이션
    private Address address;

    @OneToMany(mappedBy = "member")  // 일대다 관계
    private List<Order> orders = new ArrayList<>();
}
