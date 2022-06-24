package ezenwebcws.dto;

import ezenwebcws.domain.member.MemberEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class LoginDto implements UserDetails { // 로그인 세션에 넣을 Dto 생성
                    // UserDetails -> authorities 필수 필드 선언


    private int mno; // 회원번호
    private String mid; // 회원아이디
    private String mname; // 회원이름
    private String mpassword; // 회원비밀번호
    private final Set<GrantedAuthority> authorities ;

    public LoginDto(MemberEntity memberEntity, Collection< ? extends GrantedAuthority> authorityList) {
        this.mno = memberEntity.getMno();
        this.mid = memberEntity.getMid();
        this.mname = memberEntity.getMname();
        this.mpassword = memberEntity.getMpassword();
        this.authorities = Collections.unmodifiableSet(new LinkedHashSet<>(authorityList));
    }

//    // 인증 검색
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }

    // 패스워드 반환
    @Override
    public String getPassword() {
        return this.mpassword;
    }

    // 아이디 반환
    @Override
    public String getUsername() {
        return this.mid;
    }

    // 계정 인증 만료 여부 [ true : 만료되지 않음 ]
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠김 여부 [ true : 잠겨있지 않음 ]
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정 패스워드 만료 여부 확인 [ true : 만료되지 않음 ]
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 사용 가능 여부 확인 [ true : 사용 가능 ]
    @Override
    public boolean isEnabled() {
        return true;
    }
}
