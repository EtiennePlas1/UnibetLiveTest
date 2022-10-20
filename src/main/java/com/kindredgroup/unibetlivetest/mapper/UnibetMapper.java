package com.kindredgroup.unibetlivetest.mapper;

import fr.xebia.extras.selma.Mapper;

import com.kindredgroup.unibetlivetest.entity.Bet;
import com.kindredgroup.unibetlivetest.entity.Customer;
import com.kindredgroup.unibetlivetest.entity.Event;
import com.kindredgroup.unibetlivetest.entity.Market;
import com.kindredgroup.unibetlivetest.entity.Selection;
import com.kindredgroup.unibetlivetest.types.SelectionState;

import fr.xebia.extras.selma.IgnoreMissing;

/** 
 * Interface de mapping des objets DTO et DAO
 */ 
@Mapper(withIgnoreMissing = IgnoreMissing.ALL)
public interface UnibetMapper {

    com.kindredgroup.unibetlivetest.v1.model.Event asEvent(Event in);

    Event asInternalEvent(com.kindredgroup.unibetlivetest.v1.model.Event in);

    com.kindredgroup.unibetlivetest.v1.model.Customer asCustomer(Customer in);

    Customer asInternalCustomer(com.kindredgroup.unibetlivetest.v1.model.Customer in);

    com.kindredgroup.unibetlivetest.v1.model.Bet asBet(Bet in);

    Bet asInternalBet(com.kindredgroup.unibetlivetest.v1.model.Bet in);

    com.kindredgroup.unibetlivetest.v1.model.Market asMarket(Market in);

    Market asInternalMarket(com.kindredgroup.unibetlivetest.v1.model.Market in);

    com.kindredgroup.unibetlivetest.v1.model.Selection asSelection(Selection in);

    Selection asInternalSelection(com.kindredgroup.unibetlivetest.v1.model.Selection in);
    
    SelectionState asInternalSelectionState(com.kindredgroup.unibetlivetest.v1.model.SelectionState in);

}
