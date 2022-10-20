package com.kindredgroup.unibetlivetest.repository;

import com.kindredgroup.unibetlivetest.entity.Selection;
import com.kindredgroup.unibetlivetest.types.SelectionState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SelectionRepository extends JpaRepository<Selection, Integer> {

    List<Selection> getSelectionByStateEquals(SelectionState state);
    
    @Query(value = "select s from Selection s, Bet b where b.selection.id = s.id and s.state = 'CLOSED' and b.id IS NOT NULL and b.betState IS NULL")
    List<Selection> getClosedSelectionWithBets();

    Optional<Selection> findById(Long id);
    
}
