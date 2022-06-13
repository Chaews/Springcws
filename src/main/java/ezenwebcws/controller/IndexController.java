package ezenwebcws.controller;

import ezenwebcws.dto.HelloDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model){
        HelloDto helloDto = HelloDto.builder().name("강호동").amount(10000).build();
        model.addAttribute("data",helloDto);
        return "main";
    }

}
