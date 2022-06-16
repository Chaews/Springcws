package ezenwebcws.dto;

import lombok.*;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor @ToString
public class LoginDto { // 로그인 세션에 넣을 Dto 생성
    private int mno;
    private String mid;
    private String mname;
}
