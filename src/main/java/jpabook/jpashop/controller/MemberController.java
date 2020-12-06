package jpabook.jpashop.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
    
    private final MemberService memberService;

    @GetMapping("members/new")
    public String createForm(Model model) {

        model.addAttribute("memberForm", new MemberForm());

        return "members/createMemberForm";
        

    }

    @PostMapping("members/new") // @Valid 어노테이션과 BindingResult는 데이터 검증기능을 하는 녀석들이다 나중에 자세히 알아보자...!!
    public String create(@Valid MemberForm memberForm, BindingResult result) {

        if (result.hasErrors()) { // 에러가 있으면 return 경로로 넘겨준다
            return "members/createMemberForm";
        }

        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());

        Member member = new Member();
        member.setName(memberForm.getName());
        member.setAddress(address);

        memberService.join(member);

        return "redirect:/";

    }

}
