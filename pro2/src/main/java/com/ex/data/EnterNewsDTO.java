
package com.ex.data;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EnterNewsDTO {
    private int num;
    private String title;
    private String content;

    // FK: users.id (VARCHAR2)
    private String writer_id;

    // JOIN 결과로 가져올 값
    private String writerNickname;
    private String writerEmail;

    private LocalDateTime reg;
    private int readCount;
    private String category;
    private int hot;
    
    private int status;
    
    private String thumbnail;
}
