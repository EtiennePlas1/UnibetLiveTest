package com.kindredgroup.unibetlivetest.mapper;

import org.springframework.stereotype.Service;

import com.kindredgroup.unibetlivetest.types.SelectionState;
import com.kindredgroup.unibetlivetest.v1.model.Bet;
import com.kindredgroup.unibetlivetest.v1.model.Customer;
import com.kindredgroup.unibetlivetest.v1.model.Event;
import com.kindredgroup.unibetlivetest.v1.model.Market;
import com.kindredgroup.unibetlivetest.v1.model.Selection;

import fr.xebia.extras.selma.Selma;

/** 
 * Implémentation interface mapping objets DTO DAO
 */ 
@Service
public class UnibetMapperService implements UnibetMapper {

    private UnibetMapper mapper = Selma.getMapper(UnibetMapper.class);
    
    @Override
    public Event asEvent(com.kindredgroup.unibetlivetest.entity.Event in) {        
        return mapper.asEvent(in);
    }

    @Override
    public com.kindredgroup.unibetlivetest.entity.Event asInternalEvent(Event in) {
        return mapper.asInternalEvent(in);
    }

    @Override
    public Customer asCustomer(com.kindredgroup.unibetlivetest.entity.Customer in) {
        return mapper.asCustomer(in);
    }

    @Override
    public com.kindredgroup.unibetlivetest.entity.Customer asInternalCustomer(Customer in) {
        return mapper.asInternalCustomer(in);
    }

    @Override
    public Bet asBet(com.kindredgroup.unibetlivetest.entity.Bet in) {
        return mapper.asBet(in);
    }

    @Override
    public com.kindredgroup.unibetlivetest.entity.Bet asInternalBet(Bet in) {
        return mapper.asInternalBet(in);
    }

    @Override
    public Market asMarket(com.kindredgroup.unibetlivetest.entity.Market in) {
        return mapper.asMarket(in);
    }

    @Override
    public com.kindredgroup.unibetlivetest.entity.Market asInternalMarket(Market in) {
        return mapper.asInternalMarket(in);
    }

    @Override
    public Selection asSelection(com.kindredgroup.unibetlivetest.entity.Selection in) {
        return mapper.asSelection(in);
    }

    @Override
    public com.kindredgroup.unibetlivetest.entity.Selection asInternalSelection(Selection in) {
        return mapper.asInternalSelection(in);
    }

    @Override
    public SelectionState asInternalSelectionState(com.kindredgroup.unibetlivetest.v1.model.SelectionState in) {
        return mapper.asInternalSelectionState(in);
    }

}
