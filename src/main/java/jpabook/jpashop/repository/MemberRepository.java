package jpabook.jpashop.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;

@Repository // 컨테이너에서 자동으로 Spring Bean으로 관리
@RequiredArgsConstructor // 초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성
public class MemberRepository {
    
    private final EntityManager em;

    public void save(Member member) { // 회원 등록

        em.persist(member);

    }

    public Member findOne(Long id) { // 회원 조회 1건

        return em.find(Member.class, id);

    }

    public List<Member> findAll() { // 회원 목록 조회

        return em.createQuery("select m from Member m", Member.class).getResultList();

    }

    public List<Member> findByName(String name) { // ex) 이름으로 회원 찾기

        return em.createQuery("select m from Member m where m.name = :name", Member.class).setParameter("name", name).getResultList();

    }

}
