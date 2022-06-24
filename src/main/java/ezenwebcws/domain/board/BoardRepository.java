package ezenwebcws.domain.board;

import ezenwebcws.domain.member.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

    // 1. 검색 메소드
        // 1. .findAll() : 모든 엔티티 호출
        // 2. .findById(pk값) : 해당 pk의 엔티티 호출
        // 3. [직접 선언] : .findBy필드명(값) : 해당 필드명에서 값에 해당하는 한개의 엔티티 검색 [Oprional]
        // 4. [직접 선언] : .findAllBy필드명(값) : 해당 필드명에서 값에 해당하는 여러개 엔티티 검색 [List]
        // 5. [직접 쿼리작성] : @Query(value ="쿼리문작성", nativeQuery=true)
            // SQL에 변수 넣기
                // *필드(column)명은 변수로 불가능
                // *@Param() 생략가능
                // :변수명 , ?순서번호
                // 1. 인수로 @Param("변수명") String 변수명 -> [SQL] : 변수명
                // 2. [인수] @Param("변수명") 엔티티명 자료명 - >

    // 1. 제목 검색
//    @Query(value="select * from board where btitle like %:keyword%", nativeQuery=true)
//    List<BoardEntity> findAllBybtitle( @Param("keyword") String keyword);

    @Query(value="select * from board where cno = :cno and btitle like %:keyword%", nativeQuery=true)
    Page<BoardEntity> findBybtitle(@Param("cno") int cno, @Param("keyword") String keyword , Pageable pageable); // @Param() 없을경우 // ?1 도 가능
    // List 대신 Page 사용하는 이유 : Page 관련된 메소드 사용하기 위해

    // 2. 내용 검색
//    @Query(value="select * from board where bcontent like %:keyword%", nativeQuery=true)
//    List<BoardEntity> findAllBybcontent(@Param("keyword") String keyword);

    @Query(value="select * from board where cno = :cno and bcontent like %:keyword%", nativeQuery=true)
    Page<BoardEntity> findBybcontent( @Param("cno") int cno, @Param("keyword") String keyword , Pageable pageable); // @Param() 없을경우

    // 3. 작성자 검색
//    @Query(value="select * from board where mno = :#{#memberEntity.mno}", nativeQuery=true)
//    List<BoardEntity> findAllBymemberEntity(@Param("memberEntity") MemberEntity memberEntity); // findallby엔티티
//    @Query(value="select * from board where mno = :#{#memberEntity.mno}", nativeQuery=true)
//    List<BoardEntity> findAllBymno(@Param("memberEntity") MemberEntity memberEntity);

    @Query(value="select * from board where cno = :cno and mno = :#{#memberEntity.mno}", nativeQuery=true)
    Page<BoardEntity> findBymno(@Param("cno") int cno, @Param("memberEntity") Optional<MemberEntity> memberEntity , Pageable pageable); // @Param() 없을경우

}
