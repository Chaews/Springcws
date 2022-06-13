package ezenwebcws.controller;

import ezenwebcws.dto.MemberDto;
import ezenwebcws.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 템플릿 영역
public class MemberController {

    // 1. 로그인페이지 이동 매핑
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @Autowired
    MemberService memberService;

    // 2. 회원가입 페이지 이동 매핑
    @GetMapping("/signup")
    public String signup(){
        MemberDto memberDto = MemberDto.builder().mid("chae").mpassword("123123").mname("채우석").build();
        // 서비스 호출
        memberService.signup(memberDto);
        return "signup";
    }



}
