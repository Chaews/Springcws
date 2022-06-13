package ezenwebcws.service;

import ezenwebcws.domain.RoomEntity;
import ezenwebcws.domain.RoomRepository;
import ezenwebcws.dto.RoomDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    // 1. 룸 저장
    public boolean room_save(RoomDto roomDto){
        RoomEntity roomEntity = RoomEntity.builder().roomname(roomDto.getRoomname()).x(roomDto.getX()).y(roomDto.getY()).build();
        roomRepository.save(roomEntity);
        return true;
    }

    // 2. 룸 호출
    public JSONArray room_list(){
        JSONArray jsonArray = new JSONArray();
        // 1. 모든 엔티티 호출
        List<RoomEntity> roomEntityList = roomRepository.findAll();
        // 2. 모든 엔티티 -> json 변환
        for(RoomEntity temp : roomEntityList){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("roomname", temp.getRoomname());
            jsonObject.put("x", temp.getX());
            jsonObject.put("y", temp.getY());
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }
}
