package ezenwebcws.service;

import ezenwebcws.domain.member.MemberEntity;
import ezenwebcws.domain.member.MemberRepository;
import ezenwebcws.dto.LoginDto;
import ezenwebcws.dto.MemberDto;
import ezenwebcws.dto.OauthDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class MemberService implements UserDetailsService, OAuth2UserService<OAuth2UserRequest, OAuth2User> {
                                // UserDetailsService 인터페이스 [ 추상메소드 존재 ] : 일반회원
                                    // --> loadUserByUsername
                                // OAuth2UserService<OAuth2UserRequest, OAuth2User>

    // * 로그인 서비스 제공 메소드
    // 1. 패스워드 검증 X [ 시큐리티 제공 ]
    // 2. 아이디만 검증 처리
    // 3.
    @Override
    public UserDetails loadUserByUsername(String mid) throws UsernameNotFoundException {

        // 1. 회원 아이디로 엔티티 찾기
        Optional<MemberEntity> optional = memberRepository.findBymid(mid);
        MemberEntity memberEntity = optional.orElse(null);
                                    // Oprional 클래스 [null 관련 오류 방지 ]
                                    // 1. optional.isPresent() : null 아니면
                                    // 2. optional.orElse() : 만약에 optional 객체가 비어있으면 반환할 데이터
        // 2. 찾은 회원 엔티티의 권한[키]을 리스트에 담기
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(memberEntity.getrolekey()));
                // GrantedAuthority : 부여된 인증의 클래스
                // List<GrantedAuthority> : 부여된 인증들을 모아두기
        // 세션 부여 ????????? -> UserDetails -> 인증되면 세션 부여
        return new LoginDto(memberEntity, authorityList); // 회원 엔티티, 인증된 리스트를 세션 부여
    }

    // oAuth2 서비스 제공 메소드
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // 인증 [ 로그인 성공 ] 된
        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        // 클라이언트 id 값 [ 네이버 vs 카카오 vs 구글 ] : oauth 구분용으로 사용할 변수
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 회원정보 요청시 사용되는 JSON 키 값 호출 : 회원정보 호출시 사용되는 키 이름
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        System.out.println("클라이언트(개발자)가 등록 이름 : " + registrationId);
        System.out.println("회원정보(JSON)호출시 사용되는 키 이름 : " + userNameAttributeName);
        System.out.println("회원정보 : " + oAuth2User.getAttributes());

        // oauth2 정보 -> Dto -> entity -> DB 저장

        // 반환타입 DefaultOAuth2User (권한명, 회원인증정보, 회원정보 호출키)
            // DefaultOAuth2User, UserDetails : 반환시 인증세션 자동부여 [ SimpleGrantedAuthority : (권한) 필수 ]
        OauthDto oauthDto = OauthDto.of(  registrationId ,  userNameAttributeName  ,  oAuth2User.getAttributes()  );

        System.out.println( "oauthDto 확인 : " + oauthDto.toString() );

        //  1. 이메일로 엔티티호출
        Optional<MemberEntity> optional
                =  memberRepository.findBymemail( oauthDto.getMemail() );
        // 2. 만약에 엔티티가 없으면
        if( !optional.isPresent() ){
            memberRepository.save( oauthDto.toentity() );  // entity 저장
        }

        // 반환타입 DefaultOAuth2User ( 권한(role)명 , 회원인증정보 , 회원정보 호출키 )
        // DefaultOAuth2User , UserDetails : 반환시 인증세션 자동 부여 [ SimpleGrantedAuthority : (권한) 필수~  ]
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_MEMBER")),
                oAuth2User.getAttributes() ,
                userNameAttributeName
        );
    }


    // 다른 클래스의 메소드나 필드 호출 방법!!
    // * 메모리 할당 [ 객체 만들기 ]
    // 1. static : java 실행시 우선 할당 -> java 종료시 메모리 초기화
    // 2. 객체생성
    // 1. 클래스명 객체명 = new 클래스명();

    // 2. 객체명.set필드명 = 데이터

    // 3. @Autowired
    //    클래스명 객체명;
    @Autowired
    MemberRepository memberRepository;

    // 로직 / 트랜잭션
    @Autowired
    HttpServletRequest request; // 세션 사용을 위한 request 객체 선언





    // 1. 로그인처리 메소드 [ 시큐리티 사용하기 전 ]
//    public boolean login(String mid, String mpassword) {
//        // 1. 모든 엔티티 호출 [ java처리 / 조건처리 ]
//        List<MemberEntity> memberEntityList = memberRepository.findAll();
//        // 2. 모든 엔티티 리스트에서 입력받은 데이터와 비교한다.
//        for(MemberEntity entity : memberEntityList){
//            // 3. 아이디와 비밀번호가 동일하면
//            if(entity.getMid().equals(mid) && entity.getMpassword().equals(mpassword)){
//                LoginDto loginDto = LoginDto.builder().mno(entity.getMno()).mid(entity.getMid()).mname(entity.getMname()).build();
//                request.getSession().setAttribute("login",loginDto); // 세션이름, 데이터
//                return true; // 4. 로그인 성공
//            }
//        }
//        return false;
//    }

    @Autowired
    JavaMailSender javaMailSender;

    // 2. 회원가입 처리 메소드
    @Transactional
    public boolean signup(MemberDto memberDto) {
        // dto -> Entity [ 이유 : dto는 DB로 들어갈 수 없다 ]
        MemberEntity memberEntity = memberDto.toentity();
        memberRepository.save(memberEntity);
        // 저장여부 판단
        if (memberEntity.getMno() < 0) {
            return false;
        } else {

            StringBuilder html = new StringBuilder();
            html.append("<html><body><h1> EZEN 부동산 회원 이메일 검증 </h1>");

            Random random = new Random();
            StringBuilder 인증코드 = new StringBuilder();
            for(int i = 0 ; i < 12 ; i++){ // 12자리 문자 난수 생성
                char character =(char)((random.nextInt(26)+97)) ; // 97 ~ 122
                인증코드.append(character);
            }
            html.append("<a href='http://localhost:8082/member/email/signup/"+인증코드+"/"+memberDto.getMid()+"')>이메일검증</a>");
            html.append("</body></html>");

            memberEntity.setOauth(인증코드.toString());
//        try{
//            MimeMessage message = javaMailSender.createMimeMessage();
//            // 0 . Mime 설정
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,true,"UTF-8"); // 예외처리 발생
//            mimeMessageHelper.setFrom("내 메일 주소","Ezen 부동산");
//            mimeMessageHelper.setTo("받을사람");
//            mimeMessageHelper.setSubject("제목");
//            mimeMessageHelper.setText("<a href='#'> 회원가입 이메일 검증 </a>",true);
//            javaMailSender.send(message);
//
//        }catch(Exception e){ e.printStackTrace(); }

            return true;
        }

    }

    @Transactional
    public void authsuccess(String authkey, String mid){
        System.out.println("검증번호 : " + authkey + " / " + mid);
        // DB 업데이트
        Optional<MemberEntity> optional = memberRepository.findBymid(mid);
        if(optional.isPresent()){
            MemberEntity memberEntity = optional.get();
            if(authkey.equals(memberEntity.getOauth())){
                // 만약에 인증키와 DB내 인증키가 동일하면
                memberEntity.setOauth("Local");
            }

        }
    }

//    public void logout(){
//        //request.getSession().invalidate();
//        request.getSession().setAttribute("login",null); // 해당 세션을 null 대입
//    }

    @Transactional
    public boolean update(String mname){
        // 세션 호출
        MemberEntity memberEntity = null;
        LoginDto loginDto = (LoginDto)request.getSession().getAttribute("login");
        if(loginDto == null ){
            return false;
        }
        else {
            Optional<MemberEntity> optionalMember = memberRepository.findById(loginDto.getMno());
            memberEntity = optionalMember.get();
            memberEntity.setMname(mname); // 해당 엔티티의 필드를 수정하면 자동으로 DB도 수정된다
            return true;
        }
    }


    // 5. 회원 탈퇴
    public boolean delete(String mpassword){

        // 1. 세션 호출
        LoginDto loginDto = (LoginDto)request.getSession().getAttribute("login");
        // 2. 엔티티 호출
        Optional<MemberEntity> optionalMember =  memberRepository.findById(loginDto.getMno());
        MemberEntity memberEntity = null;
        if(optionalMember.isPresent()){
            memberEntity = optionalMember.get();
        }
        // 3. 삭제 처리 조건
        if(memberEntity.getMpassword().equals(mpassword)){ // 만약에 해당 로그인된 패스워드와 입력받은 패스워드가 동일하면
            memberRepository.delete(memberEntity);
            request.getSession().setAttribute("login",null);
            return true;
        }
        else {
            return false;
        }
    }


}
