package ezenwebcws.domain.member;

import lombok.Builder;

import javax.persistence.*;

@Builder
@Table(name = "member")
@Entity
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mno;
    private String mid;
    private String mpassword;
    private String mname;
}
