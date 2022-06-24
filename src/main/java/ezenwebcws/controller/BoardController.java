package ezenwebcws.controller;

import ezenwebcws.dto.BoardDto;
import ezenwebcws.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private HttpServletRequest request;

    /////////////////// 1. View 열기 [ 템플릿 연결 ] 매핑 ///////////////////
    // 1. 게시판 페이지 열기
    @GetMapping("/list")
    public String list(){
        return "board/list";
    }

    int selectbno = 0 ;
    // 2. 게시물 개별 조회 페이지
    @GetMapping("/view/{bno}") // URL 경로에 변수
    public String view(@PathVariable("bno") int bno){ // { } 안에서 선언된 변수는 밖에서 사용불가
        // 1. 내가 보고 있는 게시물의 번호를 세션 저장
        request.getSession().setAttribute("bno", bno);
        return "board/view";
    }

    // 세션에 저장안하고 처리하기
//    @GetMapping("/view") // URL 경로에 변수
//    public String view(){ // { } 안에서 선언된 변수는 밖에서 사용불가
//        // 1. 내가 보고 있는 게시물의 번호를 세션 저장
//        return "board/view";
//    }

    // 3. 게시물 수정 페이지
    @GetMapping("/update")
    public String update(){
        return "board/update";
    }
    // 4. 게시물 쓰기 페이지
    @GetMapping("/save")
    public String save(){
        return "board/save";
    }

    //////////////////////// 2. Service 처리 매핑 /////////////////////////

    // 1. C
    @PostMapping("/save")
    @ResponseBody // 객체 반환
    public boolean save(BoardDto boardDto){

        return boardService.save(boardDto);
    }
    // 2. R
    @GetMapping("/getboardlist")
    public void getboardlist(@RequestParam("cno")int cno, @RequestParam("key")String key, @RequestParam("keyword")String keyword, @RequestParam("page")int page , HttpServletResponse response){
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(boardService.getboardlist(cno, key, keyword, page));
        }
        catch (Exception e){ e.printStackTrace(); }
    }

    @GetMapping("/getboard")
    @ResponseBody
    public void getboard(HttpServletResponse response){
        int bno = (Integer)request.getSession().getAttribute("bno");
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(boardService.getboard(bno));
        }
        catch (Exception e){ e.printStackTrace(); };
    }

    // 세션에 저장안하고 처리하기
//    @PostMapping("/view{bno}")
//    @ResponseBody
//    public void getboard(@RequestParam("bno") int bno , HttpServletResponse response){
//        try {
//            response.setContentType("application/json");
//            response.setCharacterEncoding("UTF-8");
//            response.getWriter().print(boardService.getboard(bno));
//        }
//        catch (Exception e){ e.printStackTrace(); };
//    }

    // 3. U
    @PutMapping("/update")
    @ResponseBody
    public boolean update(BoardDto boardDto){
        int bno = (Integer)request.getSession().getAttribute("bno");
        boardDto.setBno(bno);
        return boardService.update(boardDto);
    }
    // 4. D
    @DeleteMapping("/delete")
    @ResponseBody
    public boolean delete(@RequestParam("bno") int bno){
        return boardService.delete(bno);
    }

    @GetMapping("/getcategorylist")
    public void getcategorylist(HttpServletResponse response){
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(boardService.getcategorylist());
        }
        catch (Exception e){ e.printStackTrace(); }
    }
}

///////////////////////////////////////

/* URL 변수 보내기 [ method : GET 방식 ]
    1. <a href="URL매핑/변수명"></a>
    2. ajax : url : "/board/view/" + bno ;

    @GetMapping("/view/{변수명}")
    @PathVariable("bno")


*/