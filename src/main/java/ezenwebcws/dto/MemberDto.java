package ezenwebcws.dto;

import ezenwebcws.domain.member.MemberEntity;
import lombok.*;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor @ToString
public class MemberDto {
    private int mno;
    private String mid;
    private String mpassword;
    private String mname;

    public MemberEntity toentity(){
        return MemberEntity.builder().mno(this.mno).mid(this.mid).mpassword(this.mpassword).mname(this.mname).build();
    }
}
