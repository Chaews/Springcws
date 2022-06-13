package ezenwebcws.dto;

import lombok.*;

import javax.persistence.Id;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor @ToString
public class RoomDto {

    private int rno;
    private String roomname;
    private String x;
    private String y;
}
