package com.stock.db.service;

import com.stock.db.domain.PossessesVO;
import com.stock.db.dto.Possesses.PossessesDetailDto;
import com.stock.db.dto.Possesses.PossessesDto;
import com.stock.db.mapper.PossessesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PossessesService {

    private final PossessesMapper possessesMapper;

    /*
        @brief  : 사용자가 보유 중인 주식 조회
        @return : 사용자가 보유 중인 주식 목록
        @param  :
            userId : 사용자 ID
     */
    @Transactional(readOnly = true)
    public List<PossessesDetailDto> findPossessesByUserId(String userId){
        List<PossessesDetailDto> possesses = possessesMapper.findPossessesByUserId(userId);
        for(PossessesDetailDto p : possesses){
            long totalPrice = p.getCurrentPrice()*p.getQuantity();
            double evalPrice = p.getAvgPrice() * p.getQuantity();

            double gain = (p.getAvgPrice() - p.getCurrentPrice())*p.getQuantity();
            double gainRatio = (gain/p.getCurrentPrice()) * 100;

            p.setTotalPrice(totalPrice);
            p.setGain(gain);
            p.setGainRatio(gainRatio);
        }

        return possesses;
    }

}
