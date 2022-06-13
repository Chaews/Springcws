package ezenwebcws.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class MemberDto {
    private int mno;
    private String mid;
    private String mpassword;
    private String mname;
}
