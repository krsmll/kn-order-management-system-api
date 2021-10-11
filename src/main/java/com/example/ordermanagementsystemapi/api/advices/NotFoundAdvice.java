package com.example.ordermanagementsystemapi.api.advices;

import com.example.ordermanagementsystemapi.api.responses.Messages;
import com.example.ordermanagementsystemapi.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Messages notFoundHandler(NotFoundException ex) {
        Messages messages = new Messages();
        messages.addMessage(ex.getMessage());

        return messages;
    }
}
