package ezenwebcws.domain.room;

import ezenwebcws.domain.BaseTime;
import lombok.*;

import javax.persistence.*;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor @ToString
@Table(name="roomimg")
@Entity
public class RoomimgEntity extends BaseTime {

    // PK 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rimgno;
    // 이미지 이름
    private String rimg;
    // 방번호 [ FK ]
    @ManyToOne
    @JoinColumn(name="rno")
    private RoomEntity roomEntity;
}
