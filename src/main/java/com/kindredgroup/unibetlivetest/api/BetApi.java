package com.kindredgroup.unibetlivetest.api;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kindredgroup.unibetlivetest.exception.CustomException;
import com.kindredgroup.unibetlivetest.exception.ExceptionHttpTranslator;
import com.kindredgroup.unibetlivetest.service.BetService;
import com.kindredgroup.unibetlivetest.types.ExceptionType;
import com.kindredgroup.unibetlivetest.v1.api.BetsApi;
import com.kindredgroup.unibetlivetest.v1.model.BetRequest;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping(Urls.BASE_PATH)
@CrossOrigin(origins = "*")
public class BetApi implements BetsApi {
    
    @Resource
    private BetService betService;
    
    @Resource 
    private ExceptionHttpTranslator translator;
    
    public ResponseEntity<Void> addBet(BetRequest betRequest) {
        try {
            if (betRequest.getMise() == null || betRequest.getMise().signum() == -1 || betRequest.getMise().signum() == 0) {
                throw new CustomException(betRequest.getMise() == null ? "La mise est null": betRequest.getMise().signum() == -1 ? "La mise est négative" : "La mise est égale à 0"  , ExceptionType.MISE_IMPOSSIBLE);
            }

            if (betRequest.getCote() == null || betRequest.getCote().signum() == 0 || betRequest.getCote().compareTo(new BigDecimal(1)) == -1) {
                throw new CustomException(betRequest.getCote() == null ? "la cote est null": betRequest.getCote().compareTo(new BigDecimal(1)) == -1 ?"La cote est inférieure à 1" : "La cote est égale à 0"  , ExceptionType.COTE_IMPOSSIBLE);
            }           

            betService.buildBet(betRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomException e) {
            log.error(e.getMessage());
            return translator.businessException(ServletUriComponentsBuilder.fromCurrentRequestUri().build().getPath(), e);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
