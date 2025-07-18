package com.ex.data;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class EnterCommentDTO {
    private int id;                   // 댓글 번호 (PK)
    private int news_num;              // 연예뉴스 글 번호 (FK)

    private String writer_id;          // 작성자 ID
    private String writerNickname;    // 작성자 닉네임 (users.nick JOIN 결과)
    private String writerProfile;     // 작성자 프로필 이미지 경로 (users.profile_img JOIN 결과)

    private String content;           // 댓글 내용
    private LocalDateTime reg;        // 작성 시각

    private int like_count;            // 추천 수
    private int dislike_count;         // 비추천 수

    private int ref;         // 그룹 번호
    private int re_level;     // 깊이
    private int re_step;      // 출력 순서
}
