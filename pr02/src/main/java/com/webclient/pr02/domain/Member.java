package com.webclient.pr02.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Getter
//@Setter
//@ToString
@Data
public class Member {

    private Long id;
    private String userid;
    private String pw;

}
