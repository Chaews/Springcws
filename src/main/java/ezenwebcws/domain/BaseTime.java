package ezenwebcws.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


@Getter
@EntityListeners(AuditingEntityListener.class) // 해당 엔티티를 감지
@MappedSuperclass
public class BaseTime {
    // JPA : 시간 감지 [ 레코드 생성 시간 , 레코드 변화 시간 ]

    @CreatedDate // 생성 날짜/시간 주입
    private LocalDateTime createdate;

    @LastModifiedDate // 수정 날짜/시간 주입
    private LocalDateTime modifiedate;

}
