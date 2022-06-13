package ezenwebcws.dto;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Builder // 생성자 사용 규칙 X -> 생성자 만드는데 안정성 보장 [ 인수 순서, 개수 null 등등 ]
public class HelloDto {
    private String name;
    private int amount;
}
