package ezenwebcws.controller;

import ezenwebcws.dto.HelloDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 컨트롤러를 json을 반환하는 컨트롤러
public class HelloController {

    @GetMapping("/hello")
    public HelloDto hello(){
        // Dto 생성
        HelloDto helloDto = HelloDto.builder().name("유재석").amount(1000).build();
        return helloDto;
    } //

}
