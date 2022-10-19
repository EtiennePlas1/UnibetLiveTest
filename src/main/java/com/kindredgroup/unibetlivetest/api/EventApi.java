package com.kindredgroup.unibetlivetest.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kindredgroup.unibetlivetest.exception.CustomException;
import com.kindredgroup.unibetlivetest.exception.ExceptionHttpTranslator;
import com.kindredgroup.unibetlivetest.mapper.UnibetMapperService;
import com.kindredgroup.unibetlivetest.service.EventService;
import com.kindredgroup.unibetlivetest.v1.api.EventsApi;
import com.kindredgroup.unibetlivetest.v1.model.Event;
import com.kindredgroup.unibetlivetest.v1.model.Selection;
import com.kindredgroup.unibetlivetest.v1.model.SelectionState;

@RestController
@Log4j2
@RequestMapping(Urls.BASE_PATH)
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EventApi implements EventsApi {

    @Resource
    private EventService eventService;
    
    @Resource 
    private ExceptionHttpTranslator translator;
    
    @Resource
    private UnibetMapperService mapper; 
    
    public ResponseEntity<List<Selection>> findSelectionsByEventId(Long id, SelectionState state) {
        try {
            List<Selection> Selections =  eventService.getAllSelectionsFromEvent(id, state);            
            return Selections.isEmpty() ? new ResponseEntity<List<Selection>>(Selections,HttpStatus.NO_CONTENT) : new ResponseEntity<List<Selection>>(Selections, HttpStatus.OK);
        } catch (CustomException e) {
            log.error(e.getMessage());
            return translator.businessException(ServletUriComponentsBuilder.fromCurrentRequestUri().build().getPath(), e);            
        } catch (Exception e) {
            log.error("erreur findSelectionsByEventId : {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<List<Event>> getAllEvents(Boolean isLive) {
        try {
            List<Event> allEvents = eventService.getAllEvents(isLive).stream().map(e -> mapper.asEvent(e)).toList();
            return allEvents.isEmpty() ? new ResponseEntity<List<Event>>(allEvents, HttpStatus.NO_CONTENT) : new ResponseEntity<List<Event>>(allEvents, HttpStatus.OK);
        } catch (Exception e) {
            log.error("erreur getAllEvents : {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
