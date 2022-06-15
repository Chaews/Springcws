package ezenwebcws.domain.room;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Table(name="room")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity // DB내 테이블과 매핑(연결)
@ToString
public class RoomEntity { // Entity = 개체

    @Id // PK
    @GeneratedValue(strategy= GenerationType.IDENTITY) // AUTO KEY
    private int rno; // 방번호 = PK, AUTO KEY
    private String rtitle; // 방 타이틀
    private String rlat; // 위도
    private String rlng; // 경도
    private String rtrans; // 거래방식
    private String rprice; // 가격
    private String rspace; // 면적
    private String rmanagementfee; // 관리비
    private String rstructure; // 구조
    private String rcompletiondate; // 준공날짜
    private String rindate; // 입주가능일
    private String rkind; // 건물종류
    private String raddress; // 주소
    private String ractive; // 상태
    private int rfloor;  // 현재층
    private int rmaxfloor; // 건물 전체 층
    private boolean rparking; // 주차여부
    private boolean relevator; // 엘리베이터 여부
    @Column(columnDefinition = "TEXT")
    private String rcontents; // 상세설명
    @Builder.Default
    @OneToMany(mappedBy = "roomEntity",cascade = CascadeType.ALL)
    private List<RoomimgEntity> roomimgEntityList = new ArrayList<>();
}
