package org.honeyrock.domain;

import lombok.Data;

import java.util.Date;

@Data
public class MemberVO {
    private Integer uno, anqty;
    private String userid, urole, uname, userpw, unqtitle, anqtitle, userusing;
    private Date regdate, anqregdate;
    //ans.uno, userid, uname, regdate, unqtitle, anqtitle, anqty
}
