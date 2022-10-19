package com.kindredgroup.unibetlivetest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kindredgroup.unibetlivetest.entity.Bet;

@Repository
public interface BetRepository extends JpaRepository<Bet, Integer>{
    
    List<Bet> getByCustomer_idEquals(Long customer_id);
    
    @Query(value = "select b from Bet b where b.selection.id = :id and b.id IS NOT NULL and b.betState IS NULL")
    List<Bet> getBySelectionIdAndBetStateNull(Long id);
        
}
