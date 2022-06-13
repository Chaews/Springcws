package ezenwebcws.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Table(name = "hello")
@Getter
@Entity
public class HelloEntity {

    @Id // JPA : pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // JPA : autokey
    private Long id;

        // length = 필드길이 , nullable = null 포함 여부
    @Column(length = 500 , nullable = false) // jpa : Column(속성명 = 값,속성명 = 값)
    private String title;

        // columnDefinition = "TEXT" : 긴글 자료형
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;
}
