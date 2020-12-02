package jpabook.jpashop.service;

import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true) // readOnly 옵션을 사용해주면 조회 로직에는 성능을 최적화해주기 때문에 사용을 권장 단 조회 로직이 아닌 로직은 따로 어노테이션 적용
@RequiredArgsConstructor // 초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 가입
    @Transactional
    public Long join(Member member) {

        validateDuplicateMember(member); // 중복 회원 검사
        memberRepository.save(member); // 회원 가입처리 
        return member.getId(); // 아이디 반환

    }

    // 중복회원 검사
    private void validateDuplicateMember(Member member) { 

        List<Member> findMembers = memberRepository.findByName(member.getName());

        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        } else {
            System.out.println("존재하지 않는 회원입니다.");
        }
    }
    // 회원 전체 조회
    public List<Member> findMembers() {

        return memberRepository.findAll();

    }

    // 회원 한건 조회
    public Member findOne(Long Id) {

        return memberRepository.findOne(Id);

    }

}
