package ezenwebcws.domain.member;

import ezenwebcws.domain.BaseTime;
import ezenwebcws.domain.room.RoomEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor @ToString
@Table(name = "member")
@Entity
public class MemberEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mno;
    private String mid;
    private String mpassword;
    private String mname;
    @Builder.Default
    @OneToMany(mappedBy="memberEntity",cascade=CascadeType.ALL)
    private List<RoomEntity> roomEntityList = new ArrayList<RoomEntity>();
}
