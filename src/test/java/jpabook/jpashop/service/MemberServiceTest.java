package jpabook.jpashop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;
    
    @Test
    //@Rollback(false) // 롤백을 안해주면 DB 트렌젝션이 커밋되는 순간 JPA영속성의 insert문이 처리된다고한다.. 그렇기때문에 테스트를 위해 @Rollback 어노테이션 추가!!!
    public void 회원가입() throws Exception {

        Member member = new Member();
        member.setName("kim");

        Long savedId = memberService.join(member);

        
        em.flush(); // flush() 해주면 DB에 반영해주고 Rollback 해주기때문에 콘솔로그에서 insert문이 제대로 수행되는것을 확인할수있다 최종적인 DB반영은 X
        // 리턴받은 savedId가 memberRepository에서 찾은 findOne(savedId)가 같은 결과가 있는지 검사 true / false
        assertEquals(member, memberRepository.findOne(savedId));

    }

    @Test
    public void 중복회원예외() throws Exception {

        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        memberService.join(member1);
        try{

            memberService.join(member2); // 예외가 발생해야하는 구간!

        } catch (IllegalStateException e) {

            System.out.println("catch문 걸림");

            return;

        }
        
        fail("예외가 발생해야 한다");

    }


}
