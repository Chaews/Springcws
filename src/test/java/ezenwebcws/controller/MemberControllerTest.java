package ezenwebcws.controller;

import ezenwebcws.dto.LoginDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest  {

    @Autowired
    MockMvc mvc;

    @Test
    void login() throws Exception {
        mvc.perform(get("/member/login")).andDo(print());
    }

    @Test
    void logout() throws Exception {
        mvc.perform(get("/member/logout")).andDo(print());
    }

    @Test
    void testLogin() throws Exception {
        mvc.perform(post("/member/login").param("mid","chae").param("mpassword","123")).andDo(print());
    }

    @Test
    void signup() throws Exception {
        mvc.perform(get("/member/signup")).andDo(print());
    }

    @Test
    void save() throws Exception {
        mvc.perform(post("/member/signup")
                .param("mid","테스터")
                .param("mpassword","123")
                .param("mname","테스터이름")).andDo(print());
    }

    @Test
    void update() throws Exception {
        mvc.perform(get("/member/update")).andDo(print());
    }

//    @Test
//    void testUpdate() throws Exception {
//        MockHttpSession session = new MockHttpSession();
//        session.setAttribute("login", LoginDto.builder().mno(1).mid("chae").mname("테스터").build());
//        mvc.perform(put("/member/update").param("mname","수정왜안돼").session(session)).andDo(print());
//    }

    @Test
    void info() throws Exception {
        mvc.perform(get("/member/info")).andDo(print());
    }

    @Test
    void myroom() throws Exception {
        mvc.perform(get("/member/myroom")).andDo(print());
    }

    @Test
    void testdelete() throws Exception {
        mvc.perform(get("/member/delete")).andDo(print());
    }

//    @Test
//    void testDeletetwo() throws Exception {
//        MockHttpSession session = new MockHttpSession();
//        session.setAttribute("login", LoginDto.builder().mno(1).mid("chae").mname("테스터").build());
//        mvc.perform(delete("/member/delete").param("mpassword","123").session(session)).andDo(print());
//    }
}