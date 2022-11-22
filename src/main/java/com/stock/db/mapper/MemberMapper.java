package com.stock.db.mapper;

import com.stock.db.domain.MemberVO;
import com.stock.db.dto.MemberSignUpDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    MemberVO findById(String id);

    int insertMember(MemberSignUpDto memberSignUpDto);

}
