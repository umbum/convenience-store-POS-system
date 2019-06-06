package com.umbum.pos.exception;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Response> handleDefaultRuntimeException(final RuntimeException e,
        final HttpServletRequest request) {
        log.error("[DefaultRuntimeExceptionHandler] {}", e.getMessage(), e);

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 특정 Exception과 그를 상속한 예외들에 대한 처리가 필요하다면
    // @ExceptionHandler(SpecificCls.class) 로 처리를 지정할 수 있다.
}
