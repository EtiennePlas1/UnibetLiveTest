package com.kindredgroup.unibetlivetest.batchs;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.kindredgroup.unibetlivetest.service.MarketService;

@Component
@Log4j2
@RequiredArgsConstructor
public class MarketBatch {

    
    @Resource
    private MarketService marketService;
    
    //Récupération et traitement de tous les paris ouverts sur toutes les séléctions fermées 
    @Scheduled(fixedRate = 5000)    
    void payBetsBatch() {
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            int betsProcessed = marketService.processAllClosedSelectionsWithBets();
            stopWatch.stop();
        	log.info("{} paris payés en {} ms", betsProcessed, stopWatch.getTotalTimeMillis());
        } catch (Exception e) {
            log.error("Erreur lors du batch de paiement des paris : {}", e.getMessage());            
        }
    }
     

}
