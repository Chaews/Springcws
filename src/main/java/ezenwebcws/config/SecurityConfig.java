package ezenwebcws.config;

import ezenwebcws.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
                                    // 웹 시큐리티 설정 관련

    // 암호화 제공 [ 특정 필드 암호화 ]


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http); // 슈퍼클래스의 기본설정으로 사용
        http // http
                .authorizeRequests()// 인증된 요청
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/member/info").hasRole("MEMBER")
                .antMatchers("/board/save").hasRole("MEMBER")
                .antMatchers("/**").permitAll()  // 인증이 없어도 요청 가능 = 모든 접근 허용
                .and()
                .formLogin() // 로그인 페이지 보안 설정
                .loginPage("/member/login")// 아이디 / 비밀번호를 입력받을 페이지 URL
                .loginProcessingUrl("/member/logincontroller") // 로그인 처리할 URL
                .defaultSuccessUrl("/") // 로그인 성공시 이동할 URL
                .usernameParameter("mid") // 로그인시 아이디로 입력받을 변수명 // form 사용시 name
                .passwordParameter("mpassword") // 로그인시 비밀번호로 입력받을 변수명 // form 사용시 name
                .failureUrl("/member/login/error")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .and()
                .csrf() // csrf(): 사이트간 요청 위조 [ 해킹 공격방법중 하나 ]  = 서버에게 요청할 수 있는 페이지 제한
                .ignoringAntMatchers("/member/logincontroller")
                .ignoringAntMatchers("/member/signup")
                .ignoringAntMatchers("/board/save")
                .and()
                .exceptionHandling() // 오류페이지 발생시 시큐리티가 페이지 전환
                .accessDeniedPage("/error")
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(memberService); // 해당 서비스 클래스로 유저 정보 받는다

    } // configure 메소드 end

    // 로그인 보안 서비스

    // 1.
    @Autowired
    private MemberService memberService;

    // 2.
    @Autowired
    private PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 3.
    @Override // 인증(로그인)관리 메소드
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
            // 인증할 서비스 객체 -> 패스워드 인코딩(Bcrypt 객체로)
//        super.configure(auth); // 기본값
    }
}
