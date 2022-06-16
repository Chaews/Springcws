package ezenwebcws.service;

import ezenwebcws.domain.member.MemberEntity;
import ezenwebcws.domain.member.MemberRepository;
import ezenwebcws.domain.room.RoomEntity;
import ezenwebcws.domain.room.RoomRepository;
import ezenwebcws.domain.room.RoomimgEntity;
import ezenwebcws.domain.room.RoomimgRepository;
import ezenwebcws.dto.LoginDto;
import ezenwebcws.dto.RoomDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.File;
import java.util.*;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;
    @Autowired
    RoomimgRepository roomimgRepository;
    @Autowired
    HttpServletRequest request;
    @Autowired
    MemberRepository memberRepository;
    // 1. 룸 저장
    @Transactional
    public boolean room_save(RoomDto roomDto) {

        // 현재 로그인된 세션내 dto 호출
        LoginDto loginDto = (LoginDto)request.getSession().getAttribute("login");

        // 현재 로그인 회원의 엔티티 찾기
        MemberEntity memberEntity = memberRepository.findById(loginDto.getMno()).get();

        // 1. Dto -> entity [dto는 Db에 저장할수 없으니까 ]
        RoomEntity roomEntity = roomDto.toentity();

        // 2. 저장 [ 우선적으로 룸 DB에 저장한다 ]
        roomRepository.save(roomEntity);

        // ** 현재ㅔ 로그인된 회원 엔티티를 룸 엔티티에 저장
        roomEntity.setMemberEntity(memberEntity);
        // ** 현재 로그인된 회원 엔티티내 룸리스트에 룸 엔티티 추가
        memberEntity.getRoomEntityList().add(roomEntity);

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
                try { file.transferTo( new File(filepath) );
                    // 1. 이미지 엔티티 생성
                    RoomimgEntity roomimgEntity = RoomimgEntity.builder()
                            .rimg(uuidfile)
                            .roomEntity(roomEntity)
                            .build();
                    // 2. 엔티티 세이브
                    roomimgRepository.save(roomimgEntity);
                    // 3. 이미지엔티티를 룸엔티티에 추가

                    roomEntity.getRoomimgEntityList().add(roomimgEntity);


                }catch(Exception e){ e.printStackTrace(); }


            }
        }
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
            Double dx = Double.parseDouble(entity.getRlat());
            Double dy = Double.parseDouble(entity.getRlng());
            if(ha<dx && dx < oa && qa < dy && dy < pa ) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("rno", entity.getRno()+"");
                map.put("rtitle", entity.getRtitle());
                map.put("rlng", entity.getRlat());
                map.put("rlat", entity.getRlng());
//                map.put("rtrans", entity.getRtrans());
//                map.put("rprice", entity.getRprice());
//                map.put("rspace", entity.getRspace());
//                map.put("rmanagementfee", entity.getRmanagementfee());
//                map.put("rstructure", entity.getRstructure());
//                map.put("rcompletiondate", entity.getRcompletiondate());
//                map.put("rindate", entity.getRindate());
//                map.put("rkind", entity.getRkind());
//                map.put("raddress", entity.getRaddress());
//                map.put("ractive", entity.getRactive());
//                map.put("rfloor", entity.getRfloor()+"");
//                map.put("rmaxfloor", entity.getRmaxfloor()+"");
//                map.put("rparking", entity.getRparking()+"");
//                map.put("relevator", entity.getRelevator()+"");
//                map.put("rcontents", entity.getRcontents());
                map.put("rimg",entity.getRoomimgEntityList().get(0).getRimg());
                maplist.add(map);
            }
        }
        // 5. 리스트를 map에다가 넣기
        Map<String, List<Map<String, String>>> object = new HashMap<>();
        object.put("positions", maplist);
        return object;
    }

    public JSONObject get_room(int rno){
        JSONArray rimglist = new JSONArray();
        Optional<RoomEntity> roomEntityOption = roomRepository.findById(rno);
        RoomEntity roomEntity = roomEntityOption.get();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rno",roomEntity.getRno());
        jsonObject.put("rtitle",roomEntity.getRtitle());
        jsonObject.put("rlat",roomEntity.getRlng());
        jsonObject.put("rlng",roomEntity.getRlat());
        jsonObject.put("rtrans",roomEntity.getRtrans());
        jsonObject.put("rprice",roomEntity.getRprice());
        jsonObject.put("rspace",roomEntity.getRspace());
        jsonObject.put("rmanagementfee",roomEntity.getRmanagementfee());
        jsonObject.put("rstructure",roomEntity.getRstructure());
        jsonObject.put("rcompletiondate",roomEntity.getRcompletiondate());
        jsonObject.put("rindate",roomEntity.getRindate());
        jsonObject.put("rkind",roomEntity.getRkind());
        jsonObject.put("raddress",roomEntity.getRaddress());
        jsonObject.put("ractive",roomEntity.getRactive());
        jsonObject.put("rfloor",roomEntity.getRfloor());
        jsonObject.put("rmaxfloor",roomEntity.getRmaxfloor());
//        jsonObject.put("rparking",roomEntity.getrp);
//        jsonObject.put("relevator",roomEntity.getRno());
        jsonObject.put("rcontents",roomEntity.getRcontents());
//        for(int i = 0 ; i < roomEntity.getRoomimgEntityList().size() ; i++){
//            JSONObject jsonObject2 = new JSONObject();
//            jsonObject2.put("rimg"+i,roomEntity.getRoomimgEntityList().get(i));
//            rimglist.put(jsonObject2);
//        }
//        jsonObject.put("rimg",rimglist);
        for(RoomimgEntity roomimgEntity : roomEntity.getRoomimgEntityList()){
            rimglist.put(roomimgEntity.getRimg());
        }
        jsonObject.put("rimglist" ,rimglist );
        return jsonObject;
    }

    // 현재 로그인된 회원이 등록한 방 목록 호출
    public JSONArray myroomlist(){
        JSONArray jsonArray = new JSONArray();

        //현재 로그인된 회원 엔티티 찾기
        LoginDto loginDto = (LoginDto)request.getSession().getAttribute("login");
        MemberEntity memberEntity = memberRepository.findById(loginDto.getMno()).get();
        // 찾은 회원 엔티티의 방 목록 json 형으로 변환
        for(RoomEntity entity : memberEntity.getRoomEntityList()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("rno",entity.getRno());
            jsonObject.put("rtitle",entity.getRtitle());
            jsonObject.put("rimg",entity.getRoomimgEntityList().get(0).getRimg());
            jsonObject.put("rdate",entity.getModifiedate());
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }

    @Transactional
    public boolean myroomdelete(int rno){
        RoomEntity roomEntity = roomRepository.findById(rno).get();
        if(roomEntity != null){
            roomRepository.delete(roomEntity);
            return true;
        }
        else{
            return false;
        }
    }
}