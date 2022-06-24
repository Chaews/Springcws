package ezenwebcws.dto;

import ezenwebcws.domain.board.BoardEntity;
import lombok.*;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor @ToString
public class BoardDto {
    private int bno ;
    private String btitle ;
    private String bcontent ;
    private int bview;
    private int blike;
    private String category;

    // DTO -> Entity

    public BoardEntity toBoardEntity(){
        return new BoardEntity().builder().bno(this.bno).btitle(this.btitle).bcontent(this.bcontent).bview(this.bview).blike(this.blike).build();
    }
}
