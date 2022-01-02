package com.example.bankdemo;

import com.example.bankdemo.external.nbp.EuroRateUnavailable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlingControllerAdvice {

    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE, reason = "NBP Service currently not available, please try later")
    @ExceptionHandler(EuroRateUnavailable.class)
    public void handleNbpNotAvailable() {
        // Nothing to do
    }
}
