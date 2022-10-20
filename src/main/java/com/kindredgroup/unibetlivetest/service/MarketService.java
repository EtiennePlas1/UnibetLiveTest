package com.kindredgroup.unibetlivetest.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kindredgroup.unibetlivetest.entity.Selection;

@Service
public class MarketService {
    
    @Resource
    private BetService betService;
    
    @Resource
    private SelectionService selectionService;
    
    /** 
     * 1. récupération de toutes les séléctions fermées ayant
     *    au moins un pari qui n'est ni perdu ni gagné
     *    
     * 2. paiement des paris par sélection
     */
    public int processAllClosedSelectionsWithBets() {
        List<Selection> closedSelections = selectionService.getClosedSelectionsWithBets();        
        closedSelections.forEach(s -> betService.payBets(s));
        
        return closedSelections.size();
    }
}
