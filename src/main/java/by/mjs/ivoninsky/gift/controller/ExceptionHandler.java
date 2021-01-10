package by.mjs.ivoninsky.gift.controller;

import by.mjs.ivoninsky.gift.dao.exception.DaoException;
import by.mjs.ivoninsky.gift.model.CustomResponse;
import by.mjs.ivoninsky.gift.service.exception.ServiceException;
import by.mjs.ivoninsky.gift.util.Status;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {
            ServiceException.class,
            DaoException.class
    })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        CustomResponse<Object> comment = CustomResponse.builder()
                .code(Status.DEFAULT.getCode())
                .comment(Status.DEFAULT.getMessage()).build();

        return handleExceptionInternal(ex, comment, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
