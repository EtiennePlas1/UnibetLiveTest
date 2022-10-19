package com.kindredgroup.unibetlivetest.types;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ExceptionType {
    COTE_IMPOSSIBLE(HttpStatus.BAD_REQUEST),
    MISE_IMPOSSIBLE(HttpStatus.BAD_REQUEST),
    BALANCE_INSUFFISANTE(HttpStatus.BAD_REQUEST),
    CHANGEMENT_DE_COTE(HttpStatus.BAD_REQUEST),
    SELECTION_FERMEE(HttpStatus.BAD_REQUEST),
    PARI_DEJA_EN_COURS(HttpStatus.CONFLICT),
    CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND),
    EVENT_NOT_FOUND(HttpStatus.NOT_FOUND),
    SELECTION_NOT_FOUND(HttpStatus.NOT_FOUND);

    @Getter
    final HttpStatus status;

    ExceptionType(HttpStatus status) {
        this.status = status;
    }

}
