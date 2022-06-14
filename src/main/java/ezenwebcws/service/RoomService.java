package ezenwebcws.service;

import ezenwebcws.domain.RoomEntity;
import ezenwebcws.domain.RoomRepository;
import ezenwebcws.dto.RoomDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    // 1. 룸 저장
    public boolean room_save(RoomDto roomDto) {
        RoomEntity roomEntity = RoomEntity.builder()
                .rname(roomDto.getRname())
                .x(roomDto.getX())
                .y(roomDto.getY())
                .rtype(roomDto.getRtype())
                .rprice(roomDto.getRprice())
                .rspace(roomDto.getRspace())
                .rmprice(roomDto.getRmprice())
                .rfloor(roomDto.getRfloor())
                .rtotalfloor(roomDto.getRtotalfloor())
                .rstructure(roomDto.getRstructure())
                .rdate(roomDto.getRdate())
                .rmovedate(roomDto.getRmovedate())
                .rparking(roomDto.getRparking())
                .relevator(roomDto.getRelevator())
                .rbuildingtype(roomDto.getRbuildingtype())
                .raddress(roomDto.getRaddress())
                .rdetail(roomDto.getRdetail())
                .ractive(roomDto.getRactive())
                .build();

        String uuidfile = null;
        // 첨부파일
        if(roomDto.getRimg().size() != 0) { // 첨부파일이 1개 이상이면

            // 1. 반복문을 이용한 모든 첨부파일 호출
            for(MultipartFile file : roomDto.getRimg()){
                // 파일명이 동일하면 식별 문제 발생 ~~~~~~
                    // 1. UUID 난수 생성
                UUID uuid = UUID.randomUUID();
                uuidfile = uuid.toString()+"_"+file.getOriginalFilename().replaceAll("_","-");
                    // UUID와 파일명 구분 _ 사용 [ 만약에 파일명에 _ 존재하면 문제발생 - > 파일명 _ ----> - 로 변경 ]
                // 2. 경로 설정
                String dir = "C:\\Users\\504\\IdeaProjects\\springcws\\src\\main\\resources\\static\\upload\\" ;
                String filepath = dir + uuidfile; // 실제 첨부파일 이름
                //.getOriginalFilename() : 실제 첨부파일 이름

                // 3. 첨부파일 업로드 처리
                try { file.transferTo( new File(filepath));
                roomEntity.setRimg(uuidfile);}
                catch(Exception e){ e.printStackTrace(); }


            }
        }

        roomRepository.save(roomEntity);
        return true;
    }

    // 2. 룸 호출
    // 반환타입 { 키 : [ {}, {}, {}, {} ] }
    //  JSON vs 컬렉션 프레임워크
    // { 키 : 값 } = entry --> Map 컬렉션
    // [ 요소1, 요소2, 요소3 ]  --> List 컬렉션
    // List< Map <String, String> >
    // { " positions " : [ ] }
    // Map<String, List< Map <String, String> > >
//    public JSONObject room_list(){
//        JSONArray jsonArray = new JSONArray();
//        // 1. 모든 엔티티 호출
//        List<RoomEntity> roomEntityList = roomRepository.findAll();
//        // 2. 모든 엔티티 -> json 변환
//        for(RoomEntity temp : roomEntityList){
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("roomname", temp.getRoomname());
//            jsonObject.put("lng",temp.getX());
//            jsonObject.put("lat",temp.getY());
//            jsonArray.put(jsonObject);
//        }
//        JSONObject object = new JSONObject();
//        object.put("positions",jsonArray);
//        return object;
//    }
//}

    public Map<String, List<Map<String, String>>> room_list(Map<String, String> location) {
        double ha = Double.parseDouble(location.get("ha"));
        double qa = Double.parseDouble(location.get("qa"));
        double oa = Double.parseDouble(location.get("oa"));
        double pa = Double.parseDouble(location.get("pa"));

        // 1. 모든 엔티티 꺼내오기
        List<RoomEntity> roomEntityList = roomRepository.findAll();

        List<Map<String, String>> maplist = new ArrayList<>();
        // 2. 엔티티 -> map
        for (RoomEntity entity : roomEntityList) { // 리스트에서 엔티티 하나씩 꺼내오기
            // Location 범위내 좌표만 저장하기
            Double dx = Double.parseDouble(entity.getX());
            Double dy = Double.parseDouble(entity.getY());
            if(ha<dx && dx < oa && qa < dy && dy < pa ) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("rno", entity.getRno()+"");
                map.put("rname", entity.getRname());
                map.put("lng", entity.getX());
                map.put("lat", entity.getY());
                map.put("rimg", entity.getRimg());
                map.put("rtype", entity.getRtype());
                map.put("rprice", entity.getRprice()+"");
                map.put("rspace", entity.getRspace()+"");
                map.put("rmprice", entity.getRmprice()+"");
                map.put("rfloor", entity.getRfloor()+"");
                map.put("rtotalfloor", entity.getRtotalfloor()+"");
                map.put("rstructure", entity.getRstructure());
                map.put("rdate", entity.getRdate());
                map.put("rmovedate", entity.getRmovedate());
                map.put("rparking", entity.getRparking());
                map.put("relevator", entity.getRelevator());
                map.put("rbuildingtype", entity.getRbuildingtype());
                map.put("raddress", entity.getRaddress());
                map.put("rdetail", entity.getRdetail());
                map.put("ractive", entity.getRactive());
                maplist.add(map);
            }
        }
        // 5. 리스트를 map에다가 넣기
        Map<String, List<Map<String, String>>> object = new HashMap<>();
        object.put("positions", maplist);
        return object;
    }

}