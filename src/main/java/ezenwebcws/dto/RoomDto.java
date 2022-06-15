package ezenwebcws.dto;

import ezenwebcws.domain.room.RoomEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor @ToString
public class RoomDto {

    private int rno; // 방번호 = PK, AUTO KEY
    private String rtitle; // 방 타이틀
    private String rlat; // 위도
    private String rlng; // 경도
    private String rtrans; // 거래방식
    private String rprice; // 가격
    private String rspace; // 면적
    private String rmanagementfee; // 관리비
    private String rstructure; // 구조
    private String rcompletiondate; // 준공날짜
    private String rindate; // 입주가능일
    private String rkind; // 건물종류
    private String raddress; // 주소
    private String ractive; // 상태
    private int rfloor;  // 현재층
    private int rmaxfloor; // 건물 전체 층
    private boolean rparking; // 주차여부
    private boolean relevator; // 엘리베이터 여부
    private String rcontents; // 상세설명
    private List<MultipartFile> rimg;
    // COS vs MultipartFile
    // MultipartFile : 첨부파일 저장할 수 있는 인터페이스

    // DTO -> Entity 메소드
        // 2. 빌더 패턴 [ 빌더에 포함되지 않으면 null ]
    public RoomEntity toentity(){
        return   RoomEntity.builder()
                .rtitle(this.rtitle)
                .rlat(this.rlat)
                .rlng(this.rlng)
                .rtrans(this.rtrans)
                .rprice(this.rprice)
                .rspace(this.rspace)
                .rmanagementfee(this.rmanagementfee)
                .rstructure(this.rstructure)
                .rcompletiondate(this.rcompletiondate)
                .rindate(this.rindate)
                .rkind(this.rkind)
                .raddress(this.raddress)
                .ractive(this.ractive)
                .rfloor(this.rfloor)
                .rmaxfloor(this.rmaxfloor)
                .rparking(this.rparking)
                .relevator(this.relevator)
                .rcontents(this.rcontents)
                .build();
    }
}
