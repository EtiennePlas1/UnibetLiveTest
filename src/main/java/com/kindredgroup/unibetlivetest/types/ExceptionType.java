package com.kindredgroup.unibetlivetest.types;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ExceptionType {
    COTE_IMPOSSIBLE(HttpStatus.BAD_REQUEST),
    MISE_IMPOSSIBLE(HttpStatus.BAD_REQUEST),
    BALANCE_INSUFFISANTE(453),//HTTPSTATUS personnalisé comme précisé dans le contrat d'interface.
    CHANGEMENT_DE_COTE(454),
    SELECTION_FERMEE(456),
    PARI_DEJA_EN_COURS(HttpStatus.CONFLICT),
    CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND),
    EVENT_NOT_FOUND(HttpStatus.NOT_FOUND),
    SELECTION_NOT_FOUND(HttpStatus.NOT_FOUND);

    @Getter
    final HttpStatus status;
    
    @Getter
    final int rawStatus;

    ExceptionType(HttpStatus status) {
        this.status = status;
		this.rawStatus = status.value();
    }
    
    ExceptionType(int rawStatus){
    	this.status = null;
		this.rawStatus = rawStatus;
    }

}
