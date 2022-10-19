package com.kindredgroup.unibetlivetest.service;

import com.kindredgroup.unibetlivetest.entity.Selection;
import com.kindredgroup.unibetlivetest.exception.CustomException;
import com.kindredgroup.unibetlivetest.repository.SelectionRepository;
import com.kindredgroup.unibetlivetest.types.ExceptionType;
import com.kindredgroup.unibetlivetest.types.SelectionState;
import com.kindredgroup.unibetlivetest.utils.Helpers;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.Resource;

@RequiredArgsConstructor
@Component
@Log4j2
public class SelectionService {

    @Resource
    private SelectionRepository selectionRepository;

    /**
     * 1. Récupère toute les selections ouvertes
     * 2. Mis à jour la cote aléatoirement
     */

    public int updateOddsRandomly() {
        final List<Selection> selectionsOpened = selectionRepository.getSelectionByStateEquals(SelectionState.OPENED);
        
        selectionsOpened.forEach(s -> {
            s.setCurrentOdd(Helpers.updateOddRandomly(s.getCurrentOdd()));
            selectionRepository.save(s);
        });
        
        return selectionsOpened.size();
    }

    /**
     * 1. Récupère toute les selections ouvertes
     * 2. Ferme 5 sélections aléatoirement.
     */

    public int closeOddsRandomly() {
        final List<Selection> selectionsOpened = selectionRepository.getSelectionByStateEquals(SelectionState.OPENED);
         IntStream.range(0, 5).forEach(i-> selectionRepository.save(close(selectionsOpened.get(Helpers.getRandomIndex(0, selectionsOpened.size())))));
         return selectionsOpened.size() > 5 ? 5 : selectionsOpened.size();
                   
    }
    
    private Selection close(Selection s) {
        s.setState(SelectionState.CLOSED);
        s.setResult(Helpers.setResultRandomly());
        return s;
    }
    
    public Selection findById(Long id) {
        return selectionRepository.findById(id).orElseThrow(() -> new CustomException(format("Selection %s not found", id), ExceptionType.SELECTION_NOT_FOUND));
    }
    

    public List<Selection> getClosedSelectionsWithBets() {
        return selectionRepository.getClosedSelectionWithBets();
    }
    
    public List<Selection> getSelectionByState(SelectionState state) {
        return selectionRepository.getSelectionByStateEquals(state);
    }
    
    public List<Selection> findAll(){
        return selectionRepository.findAll();
    }
}
