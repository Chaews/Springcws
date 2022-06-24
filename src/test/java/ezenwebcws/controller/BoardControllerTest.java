package ezenwebcws.controller;

import ezenwebcws.dto.BoardDto;
import ezenwebcws.dto.LoginDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
// 스프링테스트를 위한 MockMvcRequest 메소드 호출
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc // MVC 테스트중 Controller , Service, Repository , Model 가능
//@WebMvcTest // MVC 테스트중 C 가능
class BoardControllerTest {

    @Autowired
    MockMvc mvc;


    @Test
    void list() throws Exception {
        // view 없는 가정 하에 HTTP 테스트할 수 있는 메소드
        mvc.perform(get("/board/list")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void view() throws Exception {
        mvc.perform(get("/board/view/1")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void update() throws Exception {
        mvc.perform(get("/board/update")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void save() throws Exception {
        mvc.perform(get("/board/save")).andExpect(status().isOk()).andDo(print());
    }

//    @Test
//    void testSave() throws Exception {
//        MockHttpSession session = new MockHttpSession();
//        session.setAttribute("login", LoginDto.builder().mno(1).mid("chae").mname("테스터").build());
//        mvc.perform(post("/board/save").param("btitle","테스트제목").param("bcontent","테스트내용").param("category","자유게시판").session(session)).andExpect(status().isOk()).andDo(print());
//    }

    @Test
    void getboardlist() throws Exception {
        mvc.perform(get("/board/getboardlist").param("cno","1").param("key","").param("keyword","").param("page","1")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void getboard() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("bno", 41);
        mvc.perform(get("/board/getboard").session(session)).andExpect(status().isOk()).andDo(print());

    }

    @Test
    void testUpdate() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("bno", 41);
        mvc.perform(put("/board/update").param("btitle","테스트수정").param("bcontent","테스트내용수정").param("category","자유게시판").session(session)).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void testdelete() throws Exception {
        mvc.perform(delete("/board/delete").param("bno","41")).andDo(print());
    }

    @Test
    void getcategorylist() throws Exception {
        mvc.perform(get("/board/getcategorylist")).andExpect(status().isOk()).andDo(print());
    }
}