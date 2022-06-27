package ezenwebcws.domain.member;

import ezenwebcws.domain.BaseTime;
import ezenwebcws.domain.board.BoardEntity;
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
    private String memail;
    private String oauth; // 일반회원 / oauth 구분용

//    @Enumerated(EnumType.ORDINAL) // 열거형 인덱스 번호
    @Enumerated(EnumType.STRING) // 열거형 이름
    private Role role ; // 권한

    @Builder.Default
    @OneToMany(mappedBy="memberEntity",cascade=CascadeType.ALL)
    private List<RoomEntity> roomEntityList = new ArrayList<RoomEntity>();
    @Builder.Default // 빌더 사용시 초기값 설정
    @OneToMany(mappedBy = "memberEntity",cascade=CascadeType.ALL) // 1:M
    private List<BoardEntity> boardEntityList = new ArrayList<>();

    public String getrolekey(){
        return role.getKey();
    }
}
