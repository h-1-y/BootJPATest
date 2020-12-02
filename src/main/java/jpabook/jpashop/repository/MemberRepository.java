package jpabook.jpashop.repository;

import java.lang.reflect.Member;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository // 컨테이너에서 자동으로 Spring Bean으로 관리
public class MemberRepository {
    
    @PersistenceContext
    private EntityManager em;

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
