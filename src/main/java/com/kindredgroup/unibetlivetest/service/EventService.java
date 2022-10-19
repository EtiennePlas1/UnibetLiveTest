package com.kindredgroup.unibetlivetest.service;

import static java.lang.String.format;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kindredgroup.unibetlivetest.entity.Event;
import com.kindredgroup.unibetlivetest.entity.Market;
import com.kindredgroup.unibetlivetest.exception.CustomException;
import com.kindredgroup.unibetlivetest.mapper.UnibetMapperService;
import com.kindredgroup.unibetlivetest.repository.EventRepository;
import com.kindredgroup.unibetlivetest.types.ExceptionType;
import com.kindredgroup.unibetlivetest.v1.model.Selection;
import com.kindredgroup.unibetlivetest.v1.model.SelectionState;

@Service
public class EventService {
    
    @Resource
    private EventRepository eventRepository;
    
    @Resource
    private UnibetMapperService mapper; 
    
    public List<Event> getAllInternalEvents(){
        return eventRepository.findAll();
    }
    
    public List<Event> getAllLiveInternalEvents(){
        return eventRepository.findAllLive();
    }
    
    public List<Event> getAllEvents(Boolean isLive){
        if(isLive) {
            return getAllLiveInternalEvents().stream().toList();            
        }
        return getAllInternalEvents().stream().toList();
    }

    public Event getEventByid(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new CustomException(format("event %s not found", id), ExceptionType.EVENT_NOT_FOUND));
    }
    
    public List<Selection> getAllSelectionsFromEvent(Long id, SelectionState state) {
        
        if(state != null) {
            return getEventByid(id).getMarkets().stream().map(Market::getSelections).flatMap(Collection::stream).filter(s -> s.getState() == mapper.asInternalSelectionState(state)).map(s -> mapper.asSelection(s)).toList();
        }
        
        return getEventByid(id).getMarkets().stream().map(Market::getSelections).flatMap(Collection::stream).map(s -> mapper.asSelection(s)).toList();
    }
}
