package ezenwebcws.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Id;
import java.util.List;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor @ToString
public class RoomDto {

    private int rno;
    private String rname;
    private String x;
    private String y;
    private List<MultipartFile> rimg;
    private String rtype;
    private int rprice;
    private double rspace;
    private int rmprice;
    private int rfloor;
    private int rtotalfloor;
    private String rstructure;
    private String rdate;
    private String rmovedate;
    private String rparking;
    private String relevator;
    private String rbuildingtype;
    private String raddress;
    private String rdetail;
    private String ractive;

    // COS vs MultipartFile
    // MultipartFile : 첨부파일 저장할 수 있는 인터페이스

}
