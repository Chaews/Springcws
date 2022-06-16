package ezenwebcws.controller;

import ezenwebcws.dto.HelloDto;
import ezenwebcws.dto.MemberDto;
import ezenwebcws.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller // 템플릿 영역
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    // 1. 로그인페이지 이동 매핑
    @GetMapping("/login")
    public String login(){
        return "/member/login";
    }

    @GetMapping("/logout")
    public String logout(Model model){
        memberService.logout();
        //return "/main"; // 타임리프 반환
        return "redirect:/";
    }

    @PostMapping("/login")
    @ResponseBody
    public boolean login(@RequestParam("mid") String mid, @RequestParam("mpassword") String mpassword){
        return memberService.login(mid, mpassword);
    }
    @GetMapping("/signup")
    public String signup(){
        return "/member/memberwrite";
    }

    // 2. 회원가입 페이지 이동 매핑
    @PostMapping("/signup")
    @ResponseBody
    public boolean save(MemberDto memberDto, HttpServletResponse response){
        // 서비스 호출
        boolean result = memberService.signup(memberDto);

        return result;
    }

    @GetMapping("/update")
    public String update(){
        return "/member/update";
    }
    @PutMapping("/update")
    @ResponseBody
    public boolean update(@RequestParam("mname") String mname){
        return memberService.update(mname);
    }

    @GetMapping("/info")
    public String info(){
        return "/member/info";
    }

    @GetMapping("/myroom")
    public String myroom(){
        return "/member/myroom";
    }

    @GetMapping("/delete")
    public String delete(){return "/member/delete";}

    @DeleteMapping("/delete")
    @ResponseBody
    public boolean delete(@RequestParam("mpassword") String mpassword){
        return memberService.delete(mpassword);
    }
}
