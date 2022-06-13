package ezenwebcws.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Table(name="room")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class RoomEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int rno;
    private String roomname;
    private String x;
    private String y;
}
