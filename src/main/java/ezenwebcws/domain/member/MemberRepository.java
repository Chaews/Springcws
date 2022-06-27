package ezenwebcws.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {

    // 아이디를 이용한 엔티티 검색
    @Query(value="select * from member where mid = :keyword", nativeQuery=true)
//    Optional<MemberEntity> findBymid(@Param("keyword") String mid); // select
    Optional<MemberEntity> findBymid(@Param("keyword") String mid); // select

    Optional< MemberEntity > findBymemail( String email );


}

// JPARepository
    // 1. findAll() : 모든 엔티티 호출
    // 2. save(엔티티) : 해당 엔티티를 DB레코드 추가
    // 3. findById(pk값) :  : 해당 pk의 엔티티 호출
    // 4. delete(엔티티) : 해당 엔티티를 삭제 처리
    // 수정은 없다 [ 매핑된 엔티티는 JPA 자동감지 지원 ]