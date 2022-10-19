package com.kindredgroup.unibetlivetest.batchs;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kindredgroup.unibetlivetest.exception.CustomException;
import com.kindredgroup.unibetlivetest.service.BetService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@RequiredArgsConstructor
public class BetBatch {
    
    @Resource
    private BetService betService;
    
    @Scheduled(fixedRate = 2500)    
    void betRandomlyBatch() {
        try {
            betService.betRandomly();
        } catch (CustomException e) {
            log.error("Erreur lors du batch de pari : {}", e.getMessage());
        } catch (Exception e2) {
            log.error("Erreur lors du batch de pari : {}", e2.getMessage());            
        }
    }

}
