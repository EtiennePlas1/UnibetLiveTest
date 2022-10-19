package com.kindredgroup.unibetlivetest.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kindredgroup.unibetlivetest.entity.Market;
import com.kindredgroup.unibetlivetest.entity.Selection;
import com.kindredgroup.unibetlivetest.repository.MarketRepository;

@Service
public class MarketService {

    @Resource
    private MarketRepository marketRepository;
    
    @Resource
    private BetService betService;
    
    @Resource
    private SelectionService selectionService;
    
    public List<Market> getAllMarkets(){
        return marketRepository.findAll();
    }
    
    public int processAllClosedSelections() {
        List<Selection> closedSelections = selectionService.getClosedSelectionsWithBets();        
        closedSelections.forEach(s -> betService.payBets(s));
        
        return closedSelections.size();
    }
}
