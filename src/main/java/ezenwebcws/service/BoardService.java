package ezenwebcws.service;

import ezenwebcws.domain.board.BoardEntity;
import ezenwebcws.domain.board.BoardRepository;
import ezenwebcws.dto.BoardDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    // DAO 호출 // Repository 호출
    @Autowired
    private BoardRepository boardRepository;

    // 1. C [ 인수 : 게시물 dto ]
    @Transactional
    public boolean save(BoardDto boardDto){

        // 1. Dto -> entity
        int bno = boardRepository.save(boardDto.toBoardEntity()).getBno();
        if(bno >= 1){
            return true;
        }
        else{
            return false;
        }

    }

    // 2. R [ 인수 : 없음  / 반환 : 1. JSON(js 사용할경우 가능한한 JSON) 2. MAP ]
    public JSONArray getboardlist(){
        JSONArray jsonArray = new JSONArray();
        List<BoardEntity> boardEntities = boardRepository.findAll();
        for(BoardEntity entity : boardEntities){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bno",entity.getBno());
            jsonObject.put("btitle",entity.getBtitle());
            jsonObject.put("bcontent",entity.getBcontent());
            jsonObject.put("bview",entity.getBview());
            jsonObject.put("blike",entity.getBlike());
            jsonObject.put("bindate",entity.getCreatedate());
            jsonObject.put("bmodate",entity.getModifiedate());
            jsonArray.put(jsonObject);
        }
        // * 모든 엔티티 -> JSON 변환
        return jsonArray;
    }

    // 2. R : 개별조회 [ 게시물번호 ]
    public JSONObject getboard(int bno){
        Optional<BoardEntity> optional = boardRepository.findById(bno);
        BoardEntity entity = optional.get();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("bno",entity.getBno());
        jsonObject.put("btitle",entity.getBtitle());
        jsonObject.put("bcontent",entity.getBcontent());
        jsonObject.put("bview",entity.getBview());
        jsonObject.put("blike",entity.getBlike());
        jsonObject.put("bindate",entity.getCreatedate());
        jsonObject.put("bmodate",entity.getModifiedate());
        return jsonObject;
    }

    @Transactional
    // 3. U [ 인수 : 게시물 번호, 수정할 내용들 -> dto ]
    public boolean update(BoardDto boardDto){
        BoardEntity entity = boardRepository.findById(boardDto.getBno()).get();
        entity.setBtitle(boardDto.getBtitle());
        entity.setBcontent(boardDto.getBcontent());
        return true;
    }

    // 4. D [ 인수 : 삭제할 번호 ]
    @Transactional
    public boolean delete(int bno){
        BoardEntity boardEntity = boardRepository.findById(bno).get();
        boardRepository.delete(boardEntity);
        return true;
    }
}