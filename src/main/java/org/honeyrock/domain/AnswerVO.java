package org.honeyrock.domain;

import lombok.Data;

import java.util.Date;

@Data
public class AnswerVO {
    private Integer uno, unqno, anqty;
    private String anqtitle, uname;
    private Date anregdate;
}
