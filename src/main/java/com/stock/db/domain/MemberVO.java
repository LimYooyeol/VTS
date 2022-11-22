package com.stock.db.domain;


import com.stock.db.dto.MemberSignUpDto;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MemberVO {

    private int mno;

    private String id;

    private String pwd;

    private String mname;

    private String nickname;

    private boolean isAdmin;

    private LocalDate birthDate;

    private String email;

    private String phone;
    private String address;

    private LocalDateTime regDate;

    private String account;

    private long deposit;

}
