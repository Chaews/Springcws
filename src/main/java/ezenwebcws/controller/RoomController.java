package ezenwebcws.controller;

import ezenwebcws.dto.RoomDto;
import ezenwebcws.service.MemberService;
import ezenwebcws.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller // 해당 클래스가 템플릿영역으로 사용
@RequestMapping("/room")
public class RoomController {

    @GetMapping("/write")
    public String write(){ // 1. 등록 페이지로 이동
        return "room/write";
        // templates -> room -> write.html
    }

    @Autowired
    private RoomService roomService;

    @PostMapping("/write") // 2. 등록 처리
    @ResponseBody
    public String write_save(RoomDto roomDto){ // 등록 처리
                            // 요청변수중 DTO의필드와 변수명이 동일할 경우 자동 주입
                            // Dto 목ㅈ거 : 이동간 필드
       // RoomDto roomDto = RoomDto.builder().roomname(roomname).x(x).y(y).build();
        roomService.room_save(roomDto);
        return "오우야" ;
    }

    @GetMapping("/list") // 3. 방 목록 페이지 이동
    public String list(){
        return "room/list";
    }
}

/*
    ------- @RequestMapping("경로") ---------
    GetMapping : FIND, GET  [ @RequestMapping ("경로", method=RequestMethod.GET) ]
    PostMapping : SAVE      [ @RequestMapping ("경로", method=RequestMethod.POST) ]
    PutMapping : UPDATE     [ @RequestMapping ("경로", method=RequestMethod.PUT) ]
    DeleteMapping : DELETE  [ @RequestMapping ("경로", method=RequestMethod.DELETE) ]
 */

/*
    view ------------> Controller 변수 요청 방식
    1. HttpServletRequest request 이용 String roomname =  request.getParameter("roomname");
    2. @RequestParam("roomname") String roomname ;
    3. Mapping 사용시 DTO로 자동 주입 된다
        // 조건 1. : Mapping
        // 조건 2. : 요청변수명과 DTO 필드명 동일하다

 */