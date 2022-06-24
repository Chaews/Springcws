package ezenwebcws.domain.board;

import ezenwebcws.domain.BaseTime;
import ezenwebcws.domain.member.MemberEntity;
import lombok.*;

import javax.persistence.*;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor @ToString(exclude={"memberEntity","categoryEntity"})
@Table(name="board")
@Entity
public class BoardEntity extends BaseTime {

    // 엔티티에 @ToString 쓰면 overflow... 안쓰거나 (exclude={"memberEntity"})


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bno ;
    private String btitle ;
    private String bcontent ;
    private int bview;
    private int blike;
    @ManyToOne
    @JoinColumn(name="mno")
    private MemberEntity memberEntity ;
    // 첨부파일 [ 연관관계 ]
    @ManyToOne
    @JoinColumn(name="cno")
    private CategoryEntity categoryEntity;
    // 댓글 [ 연관관계 ]

}
