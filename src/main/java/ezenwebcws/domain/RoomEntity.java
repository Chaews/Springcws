package ezenwebcws.domain;

import lombok.*;

import javax.persistence.*;

@Builder
@Table(name="room")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class RoomEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int rno;
    private String rname;
    private String x;
    private String y;
    private String rimg;
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

}
