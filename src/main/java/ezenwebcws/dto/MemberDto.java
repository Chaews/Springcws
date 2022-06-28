package ezenwebcws.dto;

import ezenwebcws.domain.member.MemberEntity;
import ezenwebcws.domain.member.Role;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor @ToString
public class MemberDto {
    private int mno;
    private String mid;
    private String mpassword;
    private String mname;

    private String memail;

    public MemberEntity toentity(){

        // 패스워드 암호화
            // BCryp : 레인보우 테이블 공격 방지를 위해 솔트(Salt)를 통합한 적응형 함수 [32비트]
            // 랜덤의 Salt 부여하여 여러번 해시를 적응 --> 암호 해독 어렵다 ..
            // 보안할데이터 + 랜덤데이터 -> 다른 진수로 변환
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return MemberEntity.builder()
                .mid(this.mid)
                .mpassword(passwordEncoder.encode(this.mpassword))
                .mname(this.mname)
                .memail(this.memail)
                .role(Role.MEMBER)
                .build();
    }
}
