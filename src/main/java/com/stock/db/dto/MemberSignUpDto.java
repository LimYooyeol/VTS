package com.stock.db.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class MemberSignUpDto {
    private int mno;

    private String id;
    private String pwd;
    private String mname;
    private String nickname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private String email;
    private String phone;
    private String address;
    private String account;

    private long deposit;

    public void setBeforeInsert(PasswordEncoder passwordEncoder) {
        this.deposit = 10000000;
        this.pwd = passwordEncoder.encode(pwd);
    }
}
